package org.rdutta.inventoryservice.controller;

import lombok.RequiredArgsConstructor;
import org.rdutta.commonlibrary.dto.InventoryResponseDTO;
import org.rdutta.commonlibrary.dto.ProductRequestDTO;
import org.rdutta.inventoryservice.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final InventoryService inventoryService;

    /**
     * Check if a specific product is available in stock for a given quantity.
     *
     * @param productId the ID of the product
     * @param quantity  the requested quantity
     * @param orderId   the order ID (to correlate with response)
     * @return InventoryResponseDTO with availability status
     */
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/{productId}")
    public ResponseEntity<InventoryResponseDTO> checkInventory(@PathVariable Long productId,
                                         @RequestParam int quantity,
                                         @RequestParam Long orderId) {
        return ResponseEntity.ok( inventoryService.getInventoryStatus(productId, quantity, orderId));
    }

    /**
     * This will import all products data at once from json.
     *
     * @param products products json data
     */
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping("/import")
    public void importProducts(@RequestBody List<ProductRequestDTO> products) {
        inventoryService.bulkImport(products);
    }
}