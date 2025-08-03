package org.rdutta.orderservice.controller;

import org.rdutta.commonlibrary.dto.OrderRequestDTO;
import org.rdutta.orderservice.service.OrderServiceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    @Autowired
    private OrderServiceDAO serviceDAO;

    @PostMapping
    public ResponseEntity<String> orderPlace(@RequestBody OrderRequestDTO dto){
        serviceDAO.placeOrder(dto);
        return ResponseEntity.ok("Successfully Placed!");
    }
}
