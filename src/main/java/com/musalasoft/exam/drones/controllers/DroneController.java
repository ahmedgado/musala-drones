package com.musalasoft.exam.drones.controllers;


import com.musalasoft.exam.drones.dto.DroneDto;
import com.musalasoft.exam.drones.entities.Drone;
import com.musalasoft.exam.drones.services.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drones")
public class DroneController {

    @Autowired
    private DroneService droneService;

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity registerNewDrone(@RequestBody DroneDto droneDto) {
        try {
            Drone drone = this.droneService.registerNewDrone(droneDto);
            return new ResponseEntity<Drone>(drone, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    @ResponseBody
    public ResponseEntity updateDrone(@RequestBody DroneDto droneDto) {
        try {
            Drone drone = this.droneService.updateDrone(droneDto);
            return new ResponseEntity<Drone>(drone, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}/")
    @ResponseBody
    public ResponseEntity getDrone(@PathVariable("id") String id) {
        Drone drone = this.droneService.getDroneById(Long.parseLong(id));
        return new ResponseEntity<Drone>(drone, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity deleteDrone(@PathVariable("id") String id) {
        this.droneService.removeDroneById(Long.parseLong(id));
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/")
    @ResponseBody
    public ResponseEntity getAllDronesOrderByBattery() {

        try {
            List<DroneDto> drones = this.droneService.getAllDrones();
            return new ResponseEntity<List<DroneDto>>(drones, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);

        }
    }

    @GetMapping("/get-available")
    @ResponseBody
    public ResponseEntity getAvailableDronesForWork() {
        try {
            List<DroneDto> drones = this.droneService.getAvailableDrones();
            return new ResponseEntity<List<DroneDto>>(drones, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);

        }
    }


}
