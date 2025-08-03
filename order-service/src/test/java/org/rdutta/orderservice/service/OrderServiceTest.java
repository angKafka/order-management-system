package org.rdutta.orderservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rdutta.commonlibrary.dto.OrderRequestDTO;
import org.rdutta.commonlibrary.dto.OrderResponseDTO;
import org.rdutta.commonlibrary.enums.OrderStatus;
import org.rdutta.orderservice.command.Command;
import org.rdutta.orderservice.mapper.OrderMapper;
import org.rdutta.orderservice.model.Order;
import org.rdutta.orderservice.repository.OrderRepository;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper mapper;

    @Mock
    private Command command;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void shouldCreateOrderWithoutUserProductTables() {
        // Arrange
        OrderRequestDTO mockRequest = loadOrderRequestFromJson("PlaceOrderValid.json");

        Order mockOrder = Order.builder()
                .userId(1L)
                .productId(2L)
                .quantity(5)
                .status(OrderStatus.CREATED.name())
                .build();

        OrderResponseDTO mockResponse = new OrderResponseDTO();

        when(orderRepository.save(any(Order.class))).thenReturn(mockOrder);
        when(mapper.toResponseDTO(any(Order.class))).thenReturn(mockResponse);

        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);

        // Act
        orderService.placeOrder(mockRequest);

        // Assert
        verify(orderRepository).save(orderCaptor.capture());
        Order savedOrder = orderCaptor.getValue();

        verify(mapper).toResponseDTO(savedOrder);
        verify(command).send(mockResponse);

        assertEquals(1L, savedOrder.getUserId());
        assertEquals(2L, savedOrder.getProductId());
        assertEquals(5, savedOrder.getQuantity());
        assertEquals(OrderStatus.CREATED.name(), savedOrder.getStatus());
    }


    private OrderRequestDTO loadOrderRequestFromJson(String filePath) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath)) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(inputStream, OrderRequestDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load test data from JSON: " + filePath, e);
        }
    }

}