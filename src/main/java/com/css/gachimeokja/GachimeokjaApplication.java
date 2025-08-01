package com.css.gachimeokja;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GachimeokjaApplication {

    public static void main(String[] args) {
        SpringApplication.run(GachimeokjaApplication.class, args);
    }

}
