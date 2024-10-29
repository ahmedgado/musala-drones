package com.musalasoft.exam.drones.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Blob;

@Entity
@Data
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int weight;
    private String code;
    @Lob
    private Byte[] image;

}
