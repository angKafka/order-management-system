package org.rdutta.inventoryservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.rdutta.commonlibrary.dto.InventoryResponseDTO;
import org.rdutta.commonlibrary.dto.ProductRequestDTO;
import org.rdutta.inventoryservice.entity.Product;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "available", source = "available")
    @Mapping(target = "orderId", source = "orderId")
    InventoryResponseDTO toResponseDTO(Product product, boolean available, Long orderId);
    List<Product> toEntityList(List<ProductRequestDTO> dtoList);
}
