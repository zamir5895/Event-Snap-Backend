package com.backend.users;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeAll;
import io.github.cdimascio.dotenv.Dotenv;
@SpringBootTest
class UsersApplicationTests {

   
    @BeforeAll
    static void setupEnv() {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("SPRING_DATASOURCE_URL", dotenv.get("SPRING_DATASOURCE_URL"));
        System.setProperty("SPRING_DATASOURCE_USERNAME", dotenv.get("SPRING_DATASOURCE_USERNAME"));
        System.setProperty("SPRING_DATASOURCE_PASSWORD", dotenv.get("SPRING_DATASOURCE_PASSWORD"));
        System.setProperty("JWT_SECRET_KEY", dotenv.get("JWT_SECRET_KEY"));
        System.setProperty("SUPPORT_EMAIL", dotenv.get("SUPPORT_EMAIL"));
        System.setProperty("APP_PASSWORD", dotenv.get("APP_PASSWORD"));    
    }
    @Test
    void contextLoads() {
    }


}
