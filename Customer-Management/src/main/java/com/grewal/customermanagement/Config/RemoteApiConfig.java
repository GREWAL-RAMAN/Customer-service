package com.grewal.customermanagement.Config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@PropertySource("classpath:application.properties")
public class RemoteApiConfig {
    @Value("${test.api.username}")
    private String Username;

    @Value("${test.api.password}")
    private String Password;

    @Value("${test.api.auth.url}")
    private String authApiUrl;

    @Value("${test.api.get.url}")
    private String GetApiUrl;
}
