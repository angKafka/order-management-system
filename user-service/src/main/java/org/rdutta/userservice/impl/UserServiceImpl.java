package org.rdutta.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.rdutta.commonlibrary.dto.UserRequestDTO;
import org.rdutta.commonlibrary.dto.UserResponseDTO;
import org.rdutta.commonlibrary.dto.OrderValidationRequestDTO;
import org.rdutta.userservice.entity.PSUser;
import org.rdutta.userservice.mapper.UserMapper;
import org.rdutta.userservice.repository.UserRepository;
import org.rdutta.userservice.service.UserServiceDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserServiceDAO {

    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final UserMapper userMapper;

    @Override
    public UserResponseDTO validateUser(OrderValidationRequestDTO dto) {
        boolean isValid = userRepository.findById(dto.getUserId())
                .map(PSUser::isActive)
                .orElse(false);

        UserResponseDTO response = new UserResponseDTO();
        response.setOrderId(dto.getOrderId());
        response.setUserId(dto.getUserId());
        response.setValid(isValid);
        return response;
    }

    @Override
    public void bulkImport(List<UserRequestDTO> users) {
        List<PSUser> psUsers = users.stream().map(
                userMapper::toEntity
        ).toList();
        userRepository.saveAll(psUsers);
    }
}