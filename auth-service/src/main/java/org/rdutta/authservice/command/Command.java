package org.rdutta.authservice.command;

import org.rdutta.commonlibrary.dto.UserContextDTO;

public interface Command {
    void send(UserContextDTO userContextDTO);
}
