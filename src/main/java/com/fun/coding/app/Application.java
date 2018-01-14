package com.fun.coding.app;

import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by nizar on 1/10/18.
 */
@SpringBootApplication(scanBasePackages = {"com.fun.coding", "com.fun.coding.repository", "com.fun.coding.model"})
@EntityScan({"com.fun.coding.model", "com.fun.coding.controller"})
@EnableJpaRepositories("com.fun.coding.repository")
public class Application {
  private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
    return args -> {

      LOGGER.info("Let's inspect the beans provided by Spring Boot:");

      String[] beanNames = ctx.getBeanDefinitionNames();
      Arrays.sort(beanNames);
      for (String beanName : beanNames) {
        LOGGER.info(beanName);
      }

    };
  }
}
