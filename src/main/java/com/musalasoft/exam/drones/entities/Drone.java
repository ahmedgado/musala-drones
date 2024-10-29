package com.musalasoft.exam.drones.entities;

import com.musalasoft.exam.drones.enums.Model;
import com.musalasoft.exam.drones.enums.DroneState;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Drone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String serialNumber;
    private Model model;
    private long weightLimit;
    private int batteryCapacity;
    private DroneState droneState;
}
