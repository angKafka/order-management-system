# 🛒 Order Management System — Microservices Architecture

A scalable, event-driven order management platform built with Spring Boot and Apache Kafka, following the **Saga Orchestration Pattern** to ensure distributed consistency across multiple microservices.

---

## 📦 Microservices Included

- **Order Service** — Manages order placement and state transitions
- **Inventory Service** — Verifies product stock availability
- **User Service** — Validates user account and details
- **Notification Service** — Sends status updates and alerts
- **Common Library** — Shared DTOs, enums, and Kafka constants
- **(Optional)**: Payment Service *(can be plugged in seamlessly)*

---

## ⚙️ Tech Stack

- **Java 17**, **Spring Boot 3**
- **Apache Kafka** — Asynchronous communication
- **PostgreSQL** — Relational database
- **EHCache** — In-memory cache for orchestrator state tracking
- **MapStruct** — DTO <-> Entity mappers
- **Lombok**, **JPA/Hibernate**
- **Docker** (for containerization), **Kubernetes** (optional for orchestration)
- **Apache JMeter** (Load Testing)

---

## 🧩 Architecture Overview

- **Event-Driven**: Services communicate via Kafka topics instead of REST
- **Saga Pattern (Orchestration)**: 
  - Order Service initiates saga
  - EhCache stores partial validation results
  - On success/failure from Inventory/User, OrderSagaOrchestrator updates order state
- **Final States**: 
  - `READY_FOR_DELIVERY` if all validations succeed
  - `FAILED` if any validation fails
---

## 🔄 Workflow

1. **User places an order** via Order Service API
2. Order Service:
   - Saves order with status `CREATED`
   - Sends `OrderValidationRequestDTO` to Kafka
3. Inventory/User services consume validation request and publish responses
4. Orchestrator (Order Service):
   - Tracks responses using EhCache
   - Updates order status to `READY_FOR_DELIVERY` or `FAILED` accordingly

---

## 🚀 Running the Project

### Prerequisites
- Java 17+
- Maven
- Kafka + Zookeeper
- PostgreSQL

### Clone and Build

```bash
git clone https://github.com/yourusername/order-management-system.git
cd order-management-system
mvn clean install
