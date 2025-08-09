package org.rdutta.commonlibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderValidationWithUserContextDTO {
    private UserContextDTO userContext;
    private OrderValidationRequestDTO orderValidationRequest;
}