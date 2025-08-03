package org.rdutta.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.rdutta.commonlibrary.dto.UserRequestDTO;
import org.rdutta.userservice.service.UserServiceDAO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceDAO userService;

    @PostMapping("/import")
    public ResponseEntity<String> importUsers(@RequestBody List<UserRequestDTO> users) {
        userService.bulkImport(users);
        return ResponseEntity.ok("Users imported successfully.");
    }
}
