package com.demosoft.stlb;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Andrii_Korkoshko on 30.09.2015.
 */
@Configuration
@EnableWebMvc
public class ClientWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.setOrder(-1);
        registry.addResourceHandler("/css/**").addResourceLocations("/WEB-INF/css/");
		registry.addResourceHandler("/js/**").addResourceLocations("/WEB-INF/js/");
		registry.addResourceHandler("/images/**").addResourceLocations("/WEB-INF/images/");
		registry.addResourceHandler("/html/**").addResourceLocations("/WEB-INF/");

        registry.addResourceHandler("/shop/css/**").addResourceLocations("/WEB-INF/css/");
        registry.addResourceHandler("/shop/js/**").addResourceLocations("/WEB-INF/js/");
        registry.addResourceHandler("/shop/images/**").addResourceLocations("/WEB-INF/images/");
        registry.addResourceHandler("/shop/html/**").addResourceLocations("/WEB-INF/");
        super.addResourceHandlers(registry);
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
