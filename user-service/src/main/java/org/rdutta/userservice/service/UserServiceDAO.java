package org.rdutta.userservice.service;

import org.rdutta.commonlibrary.dto.OrderValidationRequestDTO;
import org.rdutta.commonlibrary.dto.UserRequestDTO;
import org.rdutta.commonlibrary.dto.UserResponseDTO;

import java.util.List;

public interface UserServiceDAO {
    UserResponseDTO validateUser(OrderValidationRequestDTO dto);
    void bulkImport(List<UserRequestDTO> users);
}
