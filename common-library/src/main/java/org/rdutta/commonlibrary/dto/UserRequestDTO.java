package org.rdutta.commonlibrary.dto;

import lombok.Data;

@Data
public class UserRequestDTO {
    private String name;
    private String email;
    private boolean active;
    private String password;
}
