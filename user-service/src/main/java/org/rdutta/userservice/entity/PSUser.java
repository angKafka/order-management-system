package org.rdutta.userservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "psusers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PSUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private boolean active;
}