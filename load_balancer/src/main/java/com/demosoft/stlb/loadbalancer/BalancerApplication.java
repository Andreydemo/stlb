package com.demosoft.stlb.loadbalancer;

/**
 * Created by Andrii_Korkoshko on 17.09.2015.
 */

import com.demosoft.stlb.client.scheduler.SchedulingConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@Configuration
@EnableAutoConfiguration
@SpringBootApplication
public class BalancerApplication extends SpringBootServletInitializer {

    @PostConstruct
    public void init(){
        System.out.println("MANI congigs inited");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BalancerApplication.class);
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
    }

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(BalancerApplication.class, args);
    }

}
