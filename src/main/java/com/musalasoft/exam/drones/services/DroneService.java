package com.musalasoft.exam.drones.services;

import Exceptions.BusinessException;
import com.musalasoft.exam.drones.dto.DroneDto;
import com.musalasoft.exam.drones.entities.BatteryHistoryLog;
import com.musalasoft.exam.drones.entities.Drone;
import com.musalasoft.exam.drones.enums.DroneState;
import com.musalasoft.exam.drones.repositories.BatteryHistoryLogRepo;
import com.musalasoft.exam.drones.repositories.DroneRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DroneService {
    @Autowired
    private DroneRepo droneRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BatteryHistoryLogRepo batteryHistoryLogRepo;
    @Value("${drone.battery.trip.limit}")
    private double droneBatteryTripLimit;

    public Drone registerNewDrone(DroneDto droneDto) throws BusinessException {
        if(droneDto.getId() != null)
            throw new BusinessException("Cannot provide id for drone");

        validateDroneData(droneDto);
        Drone drone = this.modelMapper.map(droneDto, Drone.class);
        drone.setDroneState(DroneState.IDLE);
        this.droneRepo.save(drone);
        return drone;
    }

    public List<DroneDto> getAllDrones() {
        List<DroneDto> dronesDtos = new ArrayList<>();
        List<Drone> drones = this.droneRepo.findAllByOrderByBatteryCapacityDesc();
        drones.forEach(drone -> {
            dronesDtos.add(modelMapper.map(drone, DroneDto.class));
        });
        return dronesDtos;
    }

    public List<DroneDto> getAvailableDrones() {
        List<DroneDto> dronesDtos = new ArrayList<>();
        List<Drone> drones = this.droneRepo.findByDroneStateAndBatteryCapacityGreaterThan(DroneState.IDLE, droneBatteryTripLimit);
        drones.forEach(drone -> {
            dronesDtos.add(modelMapper.map(drone, DroneDto.class));
        });
        return dronesDtos;
    }

    public Drone updateDrone(DroneDto droneDto) throws BusinessException {
        if (droneDto.getId() == null)
            throw new BusinessException("Drone's id cannot be empty");
        Drone originalDrone = getDroneById(droneDto.getId());
        validateDroneData(droneDto);
        Drone drone = this.modelMapper.map(droneDto, Drone.class);
        drone.setDroneState(originalDrone.getDroneState());
        this.droneRepo.save(drone);
        return drone;
    }

    private void validateDroneData(DroneDto droneDto) {
        if (droneDto.getSerialNumber() == null || droneDto.getSerialNumber().isEmpty()) {
            throw new BusinessException("Drone serial number cannot be empty");
        } else if (droneDto.getSerialNumber().length() > 100) {
            throw new BusinessException("Drone serial number length cannot be more than 100");
        } else if (droneDto.getWeightLimit() == 0 || droneDto.getWeightLimit() > 500)
            throw new BusinessException("Drone maximum weight is 500 gr");
        else if (droneDto.getBatteryCapacity() > 100)
            throw new BusinessException("Drone maximum battery capacity is 100 %");
    }

    @Scheduled(cron = "${battery.cron.check.schedule}")
    public void checkDronesBattery() {
        System.out.println(">>>>> Check batteries report start >>>>>");
        List<DroneDto> allDrones = getAllDrones();
        List<BatteryHistoryLog> historyLogs = new ArrayList<>();
        allDrones.forEach(droneDto -> {
            System.out.printf("Drone::: " + droneDto.getSerialNumber());
            BatteryHistoryLog historyLog = new BatteryHistoryLog(droneDto.getSerialNumber(), droneDto.getBatteryCapacity());
            historyLogs.add(historyLog);
        });
        this.batteryHistoryLogRepo.saveAll(historyLogs);
    }

    public List<BatteryHistoryLog> getAllHistoryOfBatteries() {
        return this.batteryHistoryLogRepo.findAll();
    }

    public Drone getDroneById(Long droneId) {
        Optional<Drone> drone = this.droneRepo.findById(droneId);
        if (drone.isPresent())
            return drone.get();
        else throw new BusinessException("Drone with id: " + droneId + " is not found");
    }

    public void removeDroneById(Long droneId) {
        this.droneRepo.deleteById(droneId);
    }
}
