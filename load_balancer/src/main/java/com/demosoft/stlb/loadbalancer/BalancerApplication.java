package com.demosoft.stlb.loadbalancer;

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

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@SpringBootApplication
@ComponentScan
@EnableAutoConfiguration
public class BalancerApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        System.out.println("SUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUKA");
        return application.sources(BalancerApplication.class);
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        System.out.println("SUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUKA");
        super.onStartup(servletContext);
    }

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(BalancerApplication.class, args);
    }

}
