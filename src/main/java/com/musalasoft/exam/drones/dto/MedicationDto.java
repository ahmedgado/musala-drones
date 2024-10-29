package com.musalasoft.exam.drones.dto;

import lombok.Data;

@Data
public class MedicationDto {
    private Long id;
    private String name;
    private int weight;
    private String code;
    private byte[] image;
}
