package org.rdutta.inventoryservice.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rdutta.commonlibrary.dto.InventoryResponseDTO;
import org.rdutta.commonlibrary.dto.ProductRequestDTO;
import org.rdutta.inventoryservice.entity.Product;
import org.rdutta.inventoryservice.mapper.ProductMapper;
import org.rdutta.inventoryservice.repository.ProductRepository;
import org.rdutta.inventoryservice.service.InventoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public boolean isProductAvailable(Long productId, int quantity) {
        return productRepository.findById(productId)
                .map(product -> product.getQuantity() >= quantity)
                .orElse(false);
    }

    @Transactional
    @Override
    public void reduceStock(Long productId, int quantity) {
        productRepository.findById(productId).ifPresent(product -> {
            product.setQuantity(product.getQuantity() - quantity);
            productRepository.save(product);
        });
    }

    @Override
    public InventoryResponseDTO getInventoryStatus(Long productId, int quantity, Long orderId) {
        return productRepository.findById(productId)
                .map(product -> productMapper.toResponseDTO(product, product.getQuantity() >= quantity, orderId))
                .orElseGet(() -> {
                    InventoryResponseDTO dto = new InventoryResponseDTO();
                    dto.setOrderId(orderId);
                    dto.setProductId(productId);
                    dto.setAvailable(false);
                    return dto;
                }
                );
    }

    @Override
    public void bulkImport(List<ProductRequestDTO> productDTOList) {
        List<Product> products = productMapper.toEntityList(productDTOList);
        productRepository.saveAll(products);
    }
}