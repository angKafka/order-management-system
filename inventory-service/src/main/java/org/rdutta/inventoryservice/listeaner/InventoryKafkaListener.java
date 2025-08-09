package org.rdutta.inventoryservice.listeaner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.rdutta.commonlibrary.constants.KafkaTopics;
import org.rdutta.commonlibrary.dto.OrderResponseDTO;
import org.rdutta.commonlibrary.dto.OrderValidationRequestDTO;
import org.rdutta.commonlibrary.dto.OrderValidationWithUserContextDTO;
import org.rdutta.commonlibrary.dto.UserContextDTO;
import org.rdutta.inventoryservice.service.InventoryService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryKafkaListener {

    private final InventoryService inventoryService;
    private final KafkaTemplate<String, OrderResponseDTO> kafkaTemplate;

    @KafkaListener(topics = KafkaTopics.ORDER_CREATED, groupId = "inventory-group", containerFactory = "kafkaListenerContainerFactory")
    public void consumeOrder(ConsumerRecord<String, OrderValidationWithUserContextDTO> record) {
        OrderResponseDTO response = null;
        OrderValidationRequestDTO dto = record.value().getOrderValidationRequest();
        UserContextDTO userContextDTO = record.value().getUserContext();
        if(userContextDTO == null || !(userContextDTO.roles().contains("ROLE_USER") || userContextDTO.roles().contains("ROLE_ADMIN"))) {
            response = new OrderResponseDTO(
                    dto.getOrderId(),
                    false,
                    "INVENTORY_INVALID"
            );
            kafkaTemplate.send(KafkaTopics.USER_VALIDATED, response);
            log.info("User validation failed for order {}: {}", dto.getOrderId(), response.isValid());
            return;
        }else{
            boolean isAvailable = inventoryService.isProductAvailable(dto.getProductId(), dto.getQuantity());
            response = new OrderResponseDTO(
                    dto.getOrderId(),
                    isAvailable,
                    isAvailable ? "INVENTORY_VALID" : "INVENTORY_INVALID"
            );
        }

        kafkaTemplate.send(KafkaTopics.INVENTORY_VALIDATED, response);
        log.info("Inventory validation result for order {}: {}", dto.getOrderId(), response.isValid());
    }
}
