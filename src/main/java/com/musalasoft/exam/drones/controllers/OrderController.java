package com.musalasoft.exam.drones.controllers;


import com.musalasoft.exam.drones.dto.OrderDto;
import com.musalasoft.exam.drones.entities.Order;
import com.musalasoft.exam.drones.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity createOrder(@RequestBody OrderDto orderDto) {
        try {
            Order order = orderService.createNewOrder(orderDto);
            return new ResponseEntity<Order>(order, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/")
    @ResponseBody
    public ResponseEntity getAllOrders() {
        try {
            List<Order> orders = orderService.getAllOrders();
            return new ResponseEntity<List<Order>>(orders, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getCurrentOrders")
    @ResponseBody
    public ResponseEntity getOrders() {
        List<Order> orders = orderService.getCurrentOrders();
        return new ResponseEntity<List<Order>>(orders, HttpStatus.OK);
    }


}
