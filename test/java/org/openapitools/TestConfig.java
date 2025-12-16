package org.openapitools;

import org.junit.Ignore;
import org.openapitools.service.UserService;
import org.openapitools.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Ignore
@Configuration
public class TestConfig {

    @Bean
    public UserService userService() {
        return new UserServiceImpl();
    }
}
