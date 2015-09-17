package com.demosoft.stlb.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrii_Korkoshko on 17.09.2015.
 */
@Configuration
@ImportResource( { "appConfigs.xml"} )
/*@PropertySource("file:InitilaNodes.properties")*/
public class Configs {


/*    @Value("#{'${nodesUrls}'.split(',')}")
    private List<String> nodesUrls = new ArrayList<>();*/

    //To resolve ${} in @Value
   /* @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        return configurer;
    }*/
}
