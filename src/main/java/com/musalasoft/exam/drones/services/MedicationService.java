package com.musalasoft.exam.drones.services;

import Exceptions.BusinessException;
import com.musalasoft.exam.drones.dto.MedicationDto;
import com.musalasoft.exam.drones.entities.Drone;
import com.musalasoft.exam.drones.entities.Medication;
import com.musalasoft.exam.drones.repositories.MedicationRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MedicationService {

    @Autowired
    private MedicationRepo medicationRepo;

    @Autowired
    private ModelMapper modelMapper;

    public Medication saveMedication (MedicationDto medicationDto)
    {
        validateMedication(medicationDto);
        Medication medication = this.modelMapper.map(medicationDto,Medication.class);
        this.medicationRepo.save(medication);
        return medication;
    }

    public List<MedicationDto> getMedications ()
    {
        List<Medication> medications = this.medicationRepo.findAll();
        List<MedicationDto> medicationDtos = this.modelMapper.map(medications,(new ArrayList<MedicationDto>()).getClass());
        return medicationDtos;
    }

    public Medication getMedicationById(Long medicationId) throws BusinessException
    {
        Optional<Medication> medication = this.medicationRepo.findById(medicationId);
        if(medication.isPresent())
            return medication.get();
        else throw new BusinessException("Medication with id: " + medicationId + " is not found");
    }

    private void validateMedication(MedicationDto medicationDto)
    {
        if (medicationDto.getName() == null || medicationDto.getName().isEmpty())
           throw new BusinessException("Medication name is mandatory");
        else if(!validateNameLettersOnly(medicationDto.getName()))
            throw new BusinessException("Medication name must be letters with _ or -");
        else if (medicationDto.getCode() == null || medicationDto.getCode().isEmpty())
            throw new BusinessException("Medication code is mandatory");
        else if(!validateCodeUpperCaseOnly(medicationDto.getCode()))
            throw new BusinessException("Medication code must be uppercase with _ or -");
    }

    private Boolean validateCodeUpperCaseOnly(String text) {
        Pattern pattern = Pattern.compile("^[A-Z0-9_-]*$");
        Matcher m = pattern.matcher(text);

        if (m.find())
            return true;
        else
            return false;
    }

    private Boolean validateNameLettersOnly(String text) {
        return Pattern.matches("^[a-zA-Z_-]*$",text);
    }

    public void deleteMedication(long id) {
        this.medicationRepo.deleteById(id);
    }
}
