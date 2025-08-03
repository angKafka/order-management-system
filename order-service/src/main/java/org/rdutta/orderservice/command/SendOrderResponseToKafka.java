package org.rdutta.orderservice.command;

import lombok.RequiredArgsConstructor;
import org.rdutta.commonlibrary.constants.KafkaTopics;
import org.rdutta.commonlibrary.dto.OrderValidationRequestDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendOrderResponseToKafka implements Command<OrderValidationRequestDTO>{
    private final KafkaTemplate<String, OrderValidationRequestDTO> kafkaTemplate;

    @Override
    public void send(OrderValidationRequestDTO dto) {
        kafkaTemplate.send(KafkaTopics.ORDER_CREATED, dto);
    }
}
