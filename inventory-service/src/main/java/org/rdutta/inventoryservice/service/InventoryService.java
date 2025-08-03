package org.rdutta.inventoryservice.service;

import org.rdutta.commonlibrary.dto.InventoryResponseDTO;
import org.rdutta.commonlibrary.dto.ProductRequestDTO;

import java.util.List;

public interface InventoryService {
    boolean isProductAvailable(Long productId, int quantity);
    void reduceStock(Long productId, int quantity);
    InventoryResponseDTO getInventoryStatus(Long productId, int quantity, Long orderId);
    void bulkImport(List<ProductRequestDTO> productDTOList);
}
