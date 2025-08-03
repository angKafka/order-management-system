package org.rdutta.commonlibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    private Long orderId;
    private boolean valid;  // ✅ this field is critical for orchestrator
    private String status;  // optional if you also want to carry string status
}