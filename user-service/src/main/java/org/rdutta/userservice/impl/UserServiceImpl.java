package org.rdutta.userservice.impl;

import lombok.RequiredArgsConstructor;
import org.rdutta.commonlibrary.dto.UserResponseDTO;
import org.rdutta.commonlibrary.dto.OrderValidationRequestDTO;
import org.rdutta.commonlibrary.entity.Users;
import org.rdutta.userservice.repository.UserRepository;
import org.rdutta.userservice.service.UserServiceDAO;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserServiceDAO {

    private final UserRepository userRepository;

    @Override
    public UserResponseDTO validateUser(OrderValidationRequestDTO dto) {
        boolean isValid = userRepository.findById(dto.getUserId())
                .map(Users::isActive)
                .orElse(false);

        UserResponseDTO response = new UserResponseDTO();
        response.setOrderId(dto.getOrderId());
        response.setUserId(dto.getUserId());
        response.setValid(isValid);
        return response;
    }
}