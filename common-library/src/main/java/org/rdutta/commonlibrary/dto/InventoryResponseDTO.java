package org.rdutta.commonlibrary.dto;

import lombok.Data;

@Data
public class InventoryResponseDTO {
    private Long orderId;
    private Long productId;
    private boolean available;
}
