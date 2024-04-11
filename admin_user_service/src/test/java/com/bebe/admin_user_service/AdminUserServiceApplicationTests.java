package com.bebe.admin_user_service;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.client.TestRestTemplate.HttpClientOption;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=update",
        "eureka.client.service-url.defaultZone=http://localhost:8761/eureka",
        "server.port=0",
        "spring.jpa.show-sql=true"
})
class AdminUserServiceApplicationTests {
    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private DataSource dataSource;

    @Test
    void contextLoads() {
        String body = this.restTemplate.getForObject("/", String.class);
        assertThat(body).isNotNull();
    }
}
