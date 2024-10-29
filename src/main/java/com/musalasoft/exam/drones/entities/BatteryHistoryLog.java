package com.musalasoft.exam.drones.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@Entity
@Data
public class BatteryHistoryLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String droneSerialNumber;
    private double batteryUsage;
    private String createdDateTime;

    public BatteryHistoryLog()
    {}
    public BatteryHistoryLog(String droneSerialNumber,double batteryUsage)
    {
        this.droneSerialNumber = droneSerialNumber;
        this.batteryUsage = batteryUsage;
        String pattern = "MM/dd/yyyy HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        this.createdDateTime = df.format(new Date());
    }
}
