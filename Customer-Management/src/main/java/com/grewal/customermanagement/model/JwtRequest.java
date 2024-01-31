package com.grewal.customermanagement.model;

import jakarta.persistence.Table;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class JwtRequest {
    private String email;
    private String password;
}
