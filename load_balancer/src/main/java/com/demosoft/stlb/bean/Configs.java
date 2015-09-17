package com.demosoft.stlb.bean;

import com.demosoft.stlb.bean.listener.SessionCreationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import javax.servlet.http.HttpSessionListener;

/**
 * Created by Andrii_Korkoshko on 17.09.2015.
 */
@Configuration
@ImportResource({"appConfigs.xml"})
public class Configs {
}
