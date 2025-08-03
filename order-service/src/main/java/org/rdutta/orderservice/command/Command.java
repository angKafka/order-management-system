package org.rdutta.orderservice.command;

public interface Command<T> {
    void send(T message);
}
