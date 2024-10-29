package com.musalasoft.exam.drones.dto;

import com.musalasoft.exam.drones.entities.Order;
import com.musalasoft.exam.drones.enums.OrderState;
import lombok.Data;

@Data
public class OrderDto {
    private Long id;
    private String customerName;
    private Long droneId;
    private Long medicationId;
    private Double latitude;
    private Double longitude;
    private OrderState orderState;
}
