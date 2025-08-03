package org.rdutta.userservice.listeaner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rdutta.commonlibrary.constants.KafkaTopics;
import org.rdutta.commonlibrary.dto.OrderResponseDTO;
import org.rdutta.commonlibrary.dto.OrderValidationRequestDTO;
import org.rdutta.userservice.service.UserServiceDAO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserKafkaListener {

    private final UserServiceDAO userService;
    private final KafkaTemplate<String, OrderResponseDTO> kafkaTemplate;

    @KafkaListener(topics = KafkaTopics.ORDER_CREATED, groupId = "user-group", containerFactory = "kafkaListenerContainerFactory")
    public void consume(OrderValidationRequestDTO dto) {
        boolean isValid = userService.validateUser(dto).isValid();

        OrderResponseDTO response = new OrderResponseDTO(
                dto.getOrderId(),
                isValid,
                isValid ? "USER_VALID" : "USER_INVALID"
        );

        kafkaTemplate.send(KafkaTopics.USER_VALIDATED, response);
        log.info("User validation for order {} sent: {}", dto.getOrderId(), isValid);
    }
}