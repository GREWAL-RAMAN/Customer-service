package com.grewal.customermanagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AuthResponse {

    @JsonProperty("access_token")
    private String accessToken;
}
