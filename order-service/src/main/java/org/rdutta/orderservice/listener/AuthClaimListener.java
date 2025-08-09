package org.rdutta.orderservice.listener;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.rdutta.commonlibrary.constants.KafkaTopics;
import org.rdutta.commonlibrary.dto.UserContextDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Getter
@Slf4j
public class AuthClaimListener {
    private UserContextDTO userContextDTO;

    @KafkaListener(topics = KafkaTopics.TOKEN_CARRIER, groupId = "order-service-group", containerFactory = "tokenKafkaListenerContainerFactory")
    public void listenToken(UserContextDTO userContextDTO) {
        log.info("Received JWT token from auth-service: {}", userContextDTO);
        this.userContextDTO = userContextDTO;
    }
}