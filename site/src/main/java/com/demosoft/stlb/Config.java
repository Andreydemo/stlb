package com.demosoft.stlb;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by demos_000 on 03.10.2015.
 */
@Configuration
@EnableAspectJAutoProxy
@EnableAsync
@EnableScheduling
public class Config {
    public Config() {
        System.out.println("Configs inited");
    }
}
