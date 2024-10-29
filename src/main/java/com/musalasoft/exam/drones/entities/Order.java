package com.musalasoft.exam.drones.entities;

import com.musalasoft.exam.drones.enums.OrderState;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    @JoinColumn
    @ManyToOne
    private Drone drone;
    @JoinColumn
    @ManyToOne
    private Medication medication;
    private Double latitude;
    private Double longitude;
    private Double distance;
    private OrderState orderState;
}
