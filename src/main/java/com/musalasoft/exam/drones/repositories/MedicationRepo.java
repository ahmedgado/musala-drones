package com.musalasoft.exam.drones.repositories;

import com.musalasoft.exam.drones.entities.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationRepo extends JpaRepository<Medication,Long> {
}
