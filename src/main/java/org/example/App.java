package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class App {

    @RequestMapping("/")
    String home() {
        return "Welcome to my Kitchen!";
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}

// https://docs.spring.io/spring-boot/tutorial/first-application/index.html#getting-started.first-application.code.spring-boot-application