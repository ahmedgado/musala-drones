package com.musalasoft.exam.drones.controllers;

import com.musalasoft.exam.drones.services.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/history")
public class HistoryLogsController {
    @Autowired
    private DroneService droneService;

    @GetMapping("/getAll")
    @ResponseBody
    public ResponseEntity getHistoryOfAllBatteries()
    {
        return new ResponseEntity<>(droneService.getAllHistoryOfBatteries(), HttpStatus.OK);
    }
}
