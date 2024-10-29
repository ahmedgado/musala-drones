package com.musalasoft.exam.drones.services;

import com.musalasoft.exam.drones.entities.Drone;
import com.musalasoft.exam.drones.entities.Order;
import com.musalasoft.exam.drones.enums.DroneState;
import com.musalasoft.exam.drones.enums.OrderState;
import com.musalasoft.exam.drones.repositories.DroneRepo;
import com.musalasoft.exam.drones.repositories.OrderRepo;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
public class OrderThread extends Thread {

    private Order order;

    private OrderRepo orderRepo;

    private DroneRepo droneRepo;

    Logger logger = LoggerFactory.getLogger(OrderThread.class);

    @Override
    public void run() {
        Drone drone = this.order.getDrone();
        drone.setDroneState(DroneState.DELIVERING);
        this.order.setOrderState(OrderState.IN_PROGRESS);
        this.droneRepo.save(drone);
        this.orderRepo.save(order);
        try {
            logger.info("start order by " + drone.getSerialNumber() + "("+drone.getBatteryCapacity()+" % )" + " to " + this.order.getLongitude() + "," + this.order.getLatitude());
            sleep((long) (order.getDistance() * 100000));
            drone.setBatteryCapacity((int) (drone.getBatteryCapacity() - (order.getDistance()/2)));
            logger.info("end order by " + drone.getSerialNumber() + "("+drone.getBatteryCapacity()+" % )" + " to " + this.order.getLongitude() + "," + this.order.getLatitude());
            drone.setDroneState(DroneState.IDLE);
            this.order.setOrderState(OrderState.COMPLETED);
            this.droneRepo.save(drone);
            this.orderRepo.save(order);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
