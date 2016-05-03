package com.demosoft.stlb.client.scheduler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Andrii_Korkoshko on 07.10.2015.
 */
@Configuration
@EnableScheduling
@ImportResource({"appConfigs.xml"})
public class SchedulingConfiguration implements SchedulingConfigurer {

    @Autowired
    private NodeStatisticTask nodeStatisticTask;

    @Bean(destroyMethod = "shutdown")
    public Executor taskExecutor() {
        return Executors.newScheduledThreadPool(100);
    }

    @Value("${defaultBalancerPort}")
    private int defaultBalancerPort;


    @PostConstruct
    private void init() {
        System.out.println(defaultBalancerPort);
    }

    public SchedulingConfiguration() {
        System.out.println(this.getClass());
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
        taskRegistrar.addTriggerTask(
                new Runnable() {
                    @Override
                    public void run() {
                        nodeStatisticTask.execute();
                    }
                },
                new Trigger() {
                    @Override
                    public Date nextExecutionTime(TriggerContext triggerContext) {
                        Calendar nextExecutionTime = new GregorianCalendar();
                        Date lastActualExecutionTime = triggerContext.lastActualExecutionTime();
                        nextExecutionTime.setTime(lastActualExecutionTime != null ? lastActualExecutionTime : new Date());
                        nextExecutionTime.add(Calendar.MILLISECOND, nodeStatisticTask.getInterval()); //you can get the value from wherever you want
                        return nextExecutionTime.getTime();
                    }
                }
        );
    }

    public int getDefaultBalancerPort() {
        return defaultBalancerPort;
    }

    public void setDefaultBalancerPort(int defaultBalancerPort) {
        this.defaultBalancerPort = defaultBalancerPort;
    }
}

