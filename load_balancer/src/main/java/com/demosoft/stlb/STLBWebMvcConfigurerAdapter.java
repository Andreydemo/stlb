package com.demosoft.stlb;

import org.springframework.stereotype.Component;
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
        super.addResourceHandlers(registry);
    }
}
