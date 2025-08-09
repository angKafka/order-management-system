package org.rdutta.userservice.listeaner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.rdutta.commonlibrary.constants.KafkaTopics;
import org.rdutta.commonlibrary.dto.OrderResponseDTO;
import org.rdutta.commonlibrary.dto.OrderValidationRequestDTO;
import org.rdutta.commonlibrary.dto.OrderValidationWithUserContextDTO;
import org.rdutta.commonlibrary.dto.UserContextDTO;
import org.rdutta.userservice.service.UserServiceDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserKafkaListener {
    private final UserServiceDAO userService;
    private final KafkaTemplate<String, OrderResponseDTO> kafkaTemplate;

    @Value("${jwt.secret}")
    private String secret;

    @KafkaListener(
            topics = KafkaTopics.ORDER_CREATED,
            groupId = "user-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(ConsumerRecord<String, OrderValidationWithUserContextDTO> record) {
        OrderValidationRequestDTO dto = record.value().getOrderValidationRequest();
        UserContextDTO userContextDTO = record.value().getUserContext();
        boolean roleAllowed = userContextDTO != null &&
                (userContextDTO.roles().contains("ROLE_USER") || userContextDTO.roles().contains("ROLE_ADMIN"));

        boolean isValid = false;
        String status;

        if (!roleAllowed) {
            status = "USER_INVALID";
        } else {
            isValid = userService.validateUser(dto).isValid();
            status = isValid ? "USER_VALID" : "USER_INVALID";
        }

        OrderResponseDTO response = new OrderResponseDTO(dto.getOrderId(), isValid, status);
        kafkaTemplate.send(KafkaTopics.USER_VALIDATED, response);
        log.info("User validation for order {} sent: {}", dto.getOrderId(), isValid);
    }
}