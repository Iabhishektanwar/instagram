package com.Instagram.configuration;

import com.Instagram.service.UserService;
import com.Instagram.service.UserServiceImplementation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfig {

    @Bean
    public UserService userService() {
        return new UserServiceImplementation();
    }

}
