package org.rdutta.userservice.service;

import org.rdutta.commonlibrary.dto.OrderValidationRequestDTO;
import org.rdutta.commonlibrary.dto.UserResponseDTO;
public interface UserServiceDAO {
    UserResponseDTO validateUser(OrderValidationRequestDTO dto);
}
