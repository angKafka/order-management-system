package org.rdutta.commonlibrary.dto;

import java.util.Set;

public record UserContextDTO(
        String username,
        Set<String> roles
) {
}
