package org.rdutta.commonlibrary.dto;

import lombok.Builder;

@Builder
public record AuthenticationResponseDTO(
        String token
) {
}
