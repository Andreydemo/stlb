package com.demosoft.stlb.loadbalancer;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Andrii_Korkoshko on 30.09.2015.
 */
@Component
public class STLBWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.setOrder(-1);
        registry.addResourceHandler("/stlb/css/**").addResourceLocations("/WEB-INF/css/");
		registry.addResourceHandler("/stlb/js/**").addResourceLocations("/WEB-INF/js/");
		registry.addResourceHandler("/stlb/bower_components/**/*").addResourceLocations("/WEB-INF/bower_components/");
        super.addResourceHandlers(registry);
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
