package org.rdutta.orderservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.rdutta.commonlibrary.dto.OrderResponseDTO;
import org.rdutta.commonlibrary.dto.OrderValidationRequestDTO;
import org.rdutta.orderservice.model.Order;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    List<OrderResponseDTO> toResponseDTOList(List<Order> order);

    @Mapping(source = "id", target = "orderId")
    OrderValidationRequestDTO toValidationRequestDTO(Order order);

    @Mapping(source = "id", target = "orderId")
    OrderResponseDTO toResponseDTO(Order order);

}
