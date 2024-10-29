package com.musalasoft.exam.drones.repositories;

import com.musalasoft.exam.drones.entities.Drone;
import com.musalasoft.exam.drones.enums.DroneState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DroneRepo extends JpaRepository<Drone,Long> {
    List<Drone> findAllByOrderByBatteryCapacityDesc();
    List<Drone> findByDroneStateAndBatteryCapacityGreaterThan(DroneState droneState,double batteryCapacity);
}
