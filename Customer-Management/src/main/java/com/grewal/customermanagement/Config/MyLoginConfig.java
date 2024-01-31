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
public class MyLoginConfig {

    @Value("${my.login.api.username}")
    private String Username;

    @Value("${my.login.api.password}")
    private String Password;

}
