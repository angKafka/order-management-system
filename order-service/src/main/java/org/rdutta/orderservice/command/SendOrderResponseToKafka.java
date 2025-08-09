package org.rdutta.orderservice.command;

import lombok.RequiredArgsConstructor;
import org.rdutta.commonlibrary.constants.KafkaTopics;
import org.rdutta.commonlibrary.dto.OrderValidationRequestDTO;
import org.rdutta.commonlibrary.dto.OrderValidationWithUserContextDTO;
import org.rdutta.commonlibrary.dto.UserContextDTO;
import org.rdutta.orderservice.listener.AuthClaimListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendOrderResponseToKafka implements Command<OrderValidationRequestDTO>{
    private final KafkaTemplate<String, OrderValidationWithUserContextDTO> kafkaTemplate;
    private final AuthClaimListener authClaimListener;
    @Override
    public void send(OrderValidationRequestDTO dto) {
        UserContextDTO userContextDTO = authClaimListener.getUserContextDTO();
        OrderValidationWithUserContextDTO payload = new OrderValidationWithUserContextDTO(userContextDTO, dto);
        kafkaTemplate.send(KafkaTopics.ORDER_CREATED,payload);
    }
}
