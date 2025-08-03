package org.rdutta.orderservice.orchestrator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.rdutta.commonlibrary.constants.KafkaTopics;
import org.rdutta.commonlibrary.dto.OrderResponseDTO;
import org.rdutta.commonlibrary.enums.OrderStatus;
import org.rdutta.commonlibrary.util.OrderValidationStatus;
import org.rdutta.orderservice.repository.OrderRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderSagaOrchestrator {
    private final Cache<String, OrderValidationStatus> validationCache;
    private final OrderRepository orderRepository;

    @KafkaListener(
            topics = KafkaTopics.INVENTORY_VALIDATED,
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void onInventoryValidated(OrderResponseDTO dto) {
        updateStatus(dto.getOrderId(), status -> status.setInventoryValid(dto.isValid()));
    }

    @KafkaListener(topics = KafkaTopics.USER_VALIDATED,
            containerFactory = "kafkaListenerContainerFactory")
    public void onUserValidated(OrderResponseDTO dto) {
        updateStatus(dto.getOrderId(), status -> status.setUserValid(dto.isValid()));
    }

    private void updateStatus(Long orderId, Consumer<OrderValidationStatus> updater) {
        OrderValidationStatus status = validationCache.get(String.valueOf(orderId));
        if (status == null) {
            log.warn("Validation status not found in cache for orderId {}. Creating new entry.", orderId);
            status = new OrderValidationStatus();
        }

        updater.accept(status);
        validationCache.put(String.valueOf(orderId), status);
        log.info("Updated status in cache for orderId {}: {}", orderId, status);

        if (status.isAllValid()) {
            orderRepository.findById(orderId)
                    .ifPresent(order -> {
                        order.setStatus(OrderStatus.COMPLETED.name());
                        orderRepository.save(order);
                        log.info("Order {} marked as READY_FOR_DELIVERY", orderId);
                        validationCache.remove(String.valueOf(orderId));
                    });
        } else if (status.hasFailure()) {
            orderRepository.findById(orderId)
                    .ifPresent(order -> {
                        order.setStatus(OrderStatus.FAILED.name());
                        orderRepository.save(order);
                        log.info("Order {} marked as FAILED", orderId);
                        validationCache.remove(String.valueOf(orderId));
                    });
        }
    }
}