package com.demosoft.stlb.scheduler;

import org.springframework.stereotype.Component;

/**
 * Created by Andrii_Korkoshko on 07.10.2015.
 */
@Component
public class NodeStatisticTask {

    int interval = 1000;

    public void execute(){
        System.out.println("NodeStatisticTask");
        interval+=500;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }
}
