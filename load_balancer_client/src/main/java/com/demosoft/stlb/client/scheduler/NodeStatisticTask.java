package com.demosoft.stlb.client.scheduler;

import com.demosoft.stlb.client.bean.STLBRequest;
import com.demosoft.stlb.client.controller.PerformanceController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;

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
        Map<String, Object> map = new HashMap<>();
        for (Attribute attribute : list.asList()) {
            map.put(attribute.getName(), attribute.getValue());
        }
        request.setSystemCpuLoad((Double) map.get(SystemParams.SystemCpuLoad.toString()));
        request.setProcessCpuLoad((Double) map.get(SystemParams.ProcessCpuLoad.toString()));
        request.setProcessCpuTime((Long) map.get(SystemParams.ProcessCpuTime.toString()));
        request.setFreePhysicalMemorySize((Long) map.get(SystemParams.FreePhysicalMemorySize.toString()));
        request.setTotalPhysicalMemorySize((Long) map.get(SystemParams.TotalPhysicalMemorySize.toString()));
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
