package com.keikei;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class KeikeiServerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(KeikeiServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
