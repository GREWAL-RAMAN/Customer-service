package com.grewal.customermanagement.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AuthRequest {

    private String login_id;
    private String password;



}
