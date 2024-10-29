package com.musalasoft.exam.drones.repositories;

import com.musalasoft.exam.drones.entities.Order;
import com.musalasoft.exam.drones.enums.OrderState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order,Long> {
    List<Order> findByOrderStateOrderByIdDesc(OrderState orderState);
}
