package org.rdutta.orderservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.ehcache.Cache;
import org.rdutta.commonlibrary.dto.OrderRequestDTO;
import org.rdutta.commonlibrary.dto.OrderResponseDTO;
import org.rdutta.commonlibrary.dto.OrderValidationRequestDTO;
import org.rdutta.commonlibrary.enums.OrderStatus;
import org.rdutta.commonlibrary.util.OrderValidationStatus;
import org.rdutta.orderservice.command.Command;
import org.rdutta.orderservice.mapper.OrderMapper;
import org.rdutta.orderservice.model.Order;
import org.rdutta.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderServiceDAO {

    private final OrderRepository orderRepository;
    private final OrderMapper mapper;
    private final Command<OrderValidationRequestDTO> command;
    private final Cache<String, OrderValidationStatus> validationCache;


    @Transactional
    @Override
    public void placeOrder(OrderRequestDTO orderRequestDTO) {
        Order order = Order.builder()
                .productId(orderRequestDTO.getProductId())
                .userId(orderRequestDTO.getUserId())
                .quantity(orderRequestDTO.getQuantity())
                .status(OrderStatus.CREATED.name())
                .build();

        Order savedOrder = orderRepository.save(order);
        validationCache.put(savedOrder.getId().toString(), new OrderValidationStatus());
        OrderValidationRequestDTO orderValidationRequestDTO = mapper.toValidationRequestDTO(savedOrder);
        command.send(orderValidationRequestDTO);
    }

    @Override
    public Optional<OrderResponseDTO> getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .map(mapper::toResponseDTO);
    }

    @Override
    public List<OrderResponseDTO> getOrdersByUserId(Long userId) {
        return orderRepository.findAllByUserId(userId).stream()
                .map(mapper::toResponseDTO)
                .toList();
    }
}