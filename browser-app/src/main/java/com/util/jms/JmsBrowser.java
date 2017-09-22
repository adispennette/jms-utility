package com.util.jms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="com.util.jms")
public class JmsBrowser {
    public static void main(String[] args) {
        SpringApplication.run(JmsBrowser.class, args);
    }
}
