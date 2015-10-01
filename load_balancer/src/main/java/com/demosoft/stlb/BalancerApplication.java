package com.demosoft.stlb;

/**
 * Created by Andrii_Korkoshko on 17.09.2015.
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

import java.util.Arrays;

@SpringBootApplication
@ComponentScan
@EnableAutoConfiguration
public class BalancerApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BalancerApplication.class);
    }

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(BalancerApplication.class, args);

        /*System.out.println("Let's inspect the beans provided by Spring Boot:");

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }*/
    }

}
