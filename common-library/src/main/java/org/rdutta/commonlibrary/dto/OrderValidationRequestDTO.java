package org.rdutta.commonlibrary.dto;

import lombok.Data;

@Data
public class OrderValidationRequestDTO {
    private Long orderId;
    private Long productId;
    private int quantity;
    private Long userId;
}
