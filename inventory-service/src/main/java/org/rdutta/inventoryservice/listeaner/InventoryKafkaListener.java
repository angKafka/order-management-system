package org.rdutta.inventoryservice.listeaner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rdutta.commonlibrary.constants.KafkaTopics;
import org.rdutta.commonlibrary.dto.OrderResponseDTO;
import org.rdutta.commonlibrary.dto.OrderValidationRequestDTO;
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
    public void consumeOrder(OrderValidationRequestDTO dto) {
        boolean isAvailable = inventoryService.isProductAvailable(dto.getProductId(), dto.getQuantity());

        OrderResponseDTO response = new OrderResponseDTO(
                dto.getOrderId(),
                isAvailable,
                isAvailable ? "INVENTORY_VALID" : "INVENTORY_INVALID"
        );

        kafkaTemplate.send(KafkaTopics.INVENTORY_VALIDATED, response);
        log.info("Inventory validation result for order {}: {}", dto.getOrderId(), response.isValid());
    }
}
