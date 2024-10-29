package com.musalasoft.exam.drones.repositories;

import com.musalasoft.exam.drones.entities.BatteryHistoryLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatteryHistoryLogRepo extends JpaRepository<BatteryHistoryLog,Long> {
}
