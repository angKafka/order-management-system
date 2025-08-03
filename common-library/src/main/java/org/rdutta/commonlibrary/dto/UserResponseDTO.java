package org.rdutta.commonlibrary.dto;
import lombok.Data;

@Data
public class UserResponseDTO {
    private Long orderId;
    private Long userId;
    private boolean valid;
}
