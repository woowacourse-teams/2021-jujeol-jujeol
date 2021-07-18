package com.jujeol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class JujeolApplication {

    public static void main(String[] args) {
        SpringApplication.run(JujeolApplication.class, args);
    }
}
