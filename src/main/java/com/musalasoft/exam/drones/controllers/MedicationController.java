package com.musalasoft.exam.drones.controllers;

import com.google.gson.Gson;
import com.musalasoft.exam.drones.dto.MedicationDto;
import com.musalasoft.exam.drones.entities.Medication;
import com.musalasoft.exam.drones.services.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/medications")
public class MedicationController {

    @Autowired
    private MedicationService medicationService;

    @PostMapping("/")
    @ResponseBody
    public ResponseEntity saveMedication(@RequestParam("image") MultipartFile image, @RequestParam("medication") String medicationDtoString) {
        try {
            Gson gson = new Gson();
            MedicationDto medicationDto = gson.fromJson(medicationDtoString, MedicationDto.class);
            medicationDto.setImage(image.getBytes());
            Medication medication = this.medicationService.saveMedication(medicationDto);
            return new ResponseEntity<Medication>(medication, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity getMedication(@PathVariable String id) {
        try {
            Medication medication = this.medicationService.getMedicationById(Long.parseLong(id));
            return new ResponseEntity<Medication>(medication, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/")
    @ResponseBody
    public ResponseEntity getMedications() {
        return new ResponseEntity<List<MedicationDto>>(medicationService.getMedications(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity deleteMedication(@PathVariable String id)
    {
        this.medicationService.deleteMedication(Long.parseLong(id));
        return new ResponseEntity(HttpStatus.OK);
    }

}
