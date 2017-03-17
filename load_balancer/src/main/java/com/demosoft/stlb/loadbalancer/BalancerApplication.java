package com.demosoft.stlb.loadbalancer;

/**
 * Created by Andrii_Korkoshko on 17.09.2015.
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

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
        int mb = 1024*1024;
        ApplicationContext ctx = SpringApplication.run(BalancerApplication.class, args);
        Runtime runtime = Runtime.getRuntime();
        //Print total available memory
        System.out.println("Total Memory:" + runtime.totalMemory() / mb);

        //Print Maximum available memory
        System.out.println("Max Memory:" + runtime.maxMemory() / mb);
    }

}
