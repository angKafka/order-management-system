package org.rdutta.orderservice.service;

import org.rdutta.commonlibrary.dto.OrderRequestDTO;
import org.rdutta.commonlibrary.dto.OrderResponseDTO;

import java.util.List;
import java.util.Optional;

public interface OrderServiceDAO {
    void placeOrder(OrderRequestDTO orderRequestDTO);
    Optional<OrderResponseDTO> getOrderById(Long orderId);
    List<OrderResponseDTO> getOrdersByUserId(Long userId);
}
