package com.demosoft.stlb.client.scheduler;

import com.demosoft.stlb.client.bean.STLBRequest;
import com.demosoft.stlb.client.controller.PerformanceController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.management.*;
import java.lang.management.ManagementFactory;

/**
 * Created by Andrii_Korkoshko on 07.10.2015.
 */
@Component
public class NodeStatisticTask {

    enum SystemParams {
        ProcessCpuLoad, SystemCpuLoad, ProcessCpuTime, FreePhysicalMemorySize, TotalPhysicalMemorySize
    }

    @Autowired
    private PerformanceController performanceController;
    private MBeanServer mbs;
    private ObjectName name;

    int interval = 10000;

    public NodeStatisticTask() {
        mbs = ManagementFactory.getPlatformMBeanServer();
        try {
            name = ObjectName.getInstance("java.lang:type=OperatingSystem");

        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
        }
    }

    public void execute() {
        AttributeList list = getSystemParams();
        STLBRequest request = new STLBRequest();
        request.setNodeSystemParams(list);
        performanceController.sendNodeStatistic(request);
        System.out.println(this.getClass() + " was performed");
    }

    private AttributeList getSystemParams() {
        try {
            return mbs.getAttributes(name, systemParamsSames());
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
        } catch (ReflectionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String[] systemParamsSames() {
        SystemParams[] systemParams = SystemParams.values();
        String[] names = new String[systemParams.length];
        for (int i = 0; i < systemParams.length; i++) {
            names[i] = systemParams[i].name();
        }
        return names;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }
}
