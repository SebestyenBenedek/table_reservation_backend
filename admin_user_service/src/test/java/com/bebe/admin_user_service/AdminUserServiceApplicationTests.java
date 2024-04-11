package com.bebe.admin_user_service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=update",
        "eureka.client.service-url.defaultZone=http://localhost:8761/eureka",
        "server.port=0",
        "spring.jpa.show-sql=true"
})
class AdminUserServiceApplicationTests {

    @Test
    void contextLoads() {
        // This test method does nothing but ensures the application context loads successfully.
        // It's a basic check to ensure the application starts up without errors.
    }
}
