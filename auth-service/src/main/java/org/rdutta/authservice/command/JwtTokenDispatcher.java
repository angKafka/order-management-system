package org.rdutta.authservice.command;

import lombok.RequiredArgsConstructor;
import org.rdutta.commonlibrary.constants.KafkaTopics;
import org.rdutta.commonlibrary.dto.UserContextDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenDispatcher implements Command{
    private final KafkaTemplate<String, UserContextDTO> kafkaTemplate;
    @Override
    public void send(UserContextDTO userContextDTO) {
        kafkaTemplate.send(KafkaTopics.TOKEN_CARRIER,userContextDTO);
    }
}
