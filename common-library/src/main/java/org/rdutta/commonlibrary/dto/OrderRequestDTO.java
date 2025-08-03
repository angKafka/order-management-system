package org.rdutta.commonlibrary.dto;

import lombok.Data;

@Data
public class OrderRequestDTO {
    private Long productId;
    private int quantity;
    private Long userId;
}
