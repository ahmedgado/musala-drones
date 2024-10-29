package com.musalasoft.exam.drones.dto;

import com.musalasoft.exam.drones.enums.Model;
import com.musalasoft.exam.drones.enums.DroneState;
import lombok.Data;

@Data
public class DroneDto {
    private Long id;
    private String serialNumber;
    private Model model;
    private long weightLimit;
    private int batteryCapacity;
    private DroneState droneState;
}
