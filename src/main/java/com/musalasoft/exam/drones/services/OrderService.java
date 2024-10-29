package com.musalasoft.exam.drones.services;

import Exceptions.BusinessException;
import com.musalasoft.exam.drones.dto.OrderDto;
import com.musalasoft.exam.drones.entities.Order;
import com.musalasoft.exam.drones.enums.OrderState;
import com.musalasoft.exam.drones.repositories.DroneRepo;
import com.musalasoft.exam.drones.repositories.OrderRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private DroneService droneService;
    @Autowired
    private DroneRepo droneRepo;
    @Autowired
    private MedicationService medicationService;

    @Value("${stock.location.latitude}")
    private double baseLatitude;

    @Value("${stock.location.longitude}")
    private double baseLongitude;

    @Value("${drone.battery.trip.limit}")
    private double droneBatteryTripLimit;

    public Order createNewOrder(OrderDto orderDto) throws BusinessException{
        TypeMap<OrderDto, Order> propertyMapper = this.modelMapper.getTypeMap(OrderDto.class, Order.class);
        if(propertyMapper == null)
            propertyMapper = this.modelMapper.createTypeMap(OrderDto.class, Order.class);

        propertyMapper.addMappings(mapper -> mapper.skip(Order::setDrone));
        propertyMapper.addMappings(mapper -> mapper.skip(Order::setMedication));
        Order order = modelMapper.map(orderDto, Order.class);
        order.setDrone(this.droneService.getDroneById(orderDto.getDroneId()));
        order.setMedication(this.medicationService.getMedicationById(orderDto.getMedicationId()));
        checkWeightAndBattery(order);
        double distance = calculateDistance(baseLatitude, baseLongitude, order.getLatitude(), order.getLongitude());
        order.setDistance(distance);
        this.orderRepo.save(order);
        this.startOrder(order);
        return order;
    }

    private void checkWeightAndBattery(Order order) throws BusinessException {
        if (order.getDrone().getWeightLimit() > order.getMedication().getWeight()) {
           if(order.getDrone().getBatteryCapacity() <= droneBatteryTripLimit)
            throw new BusinessException("Drone's battery's capacity less than " + droneBatteryTripLimit + " % ,please charge it");
        }else {
            throw new BusinessException("Medication's weight greater than drone weight limit");
        }
    }

    private void startOrder(Order order) {
        OrderThread orderThread = new OrderThread();
        orderThread.setOrder(order);
        orderThread.setOrderRepo(this.orderRepo);
        orderThread.setDroneRepo(this.droneRepo);
        orderThread.start();
    }

    double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double lat1Rad = Math.toRadians(lat1);
        double lat2Rad = Math.toRadians(lat2);
        double lon1Rad = Math.toRadians(lon1);
        double lon2Rad = Math.toRadians(lon2);

        double x = (lon2Rad - lon1Rad) * Math.cos((lat1Rad + lat2Rad) / 2);
        double y = (lat2Rad - lat1Rad);

        double EARTH_RADIUS = 6371;
        return Math.sqrt(x * x + y * y) * EARTH_RADIUS;
    }

    public List<Order> getCurrentOrders() {
        return this.orderRepo.findByOrderStateOrderByIdDesc(OrderState.IN_PROGRESS);
    }

    public List<Order> getAllOrders() {
        return this.orderRepo.findAll();
    }
}
