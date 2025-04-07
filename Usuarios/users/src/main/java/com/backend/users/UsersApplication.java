package com.backend.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;


@SpringBootApplication
public class UsersApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load(); 
        System.setProperty("SPRING_DATASOURCE_URL", dotenv.get("SPRING_DATASOURCE_URL"));
        System.setProperty("SPRING_DATASOURCE_USERNAME", dotenv.get("SPRING_DATASOURCE_USERNAME"));
        System.setProperty("SPRING_DATASOURCE_PASSWORD", dotenv.get("SPRING_DATASOURCE_PASSWORD"));
        System.setProperty("JWT_SECRET_KEY", dotenv.get("JWT_SECRET_KEY"));
        System.setProperty("SUPPORT_EMAIL", dotenv.get("SUPPORT_EMAIL"));
        System.setProperty("APP_PASSWORD", dotenv.get("APP_PASSWORD"));
        SpringApplication.run(UsersApplication.class, args);
    }

}
