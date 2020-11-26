package com.jason.application.web.firstcrawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class ETCrawlerApplication {
    public static void main(String[] args) throws InterruptedException, IOException {
        SpringApplication.run(ETCrawlerApplication.class, args);

    }
}
