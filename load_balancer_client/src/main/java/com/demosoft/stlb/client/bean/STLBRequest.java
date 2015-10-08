package com.demosoft.stlb.client.bean;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Andrii_Korkoshko on 06.10.2015.
 */
public class STLBRequest implements Serializable {

    private static final long serialVersionUID = -785259415286305761L;
    public static final String SESSION = "SESSION";
    public static final String NODE = "NODE";
    private String id;

    private Double processCpuLoad;
    private Double systemCpuLoad;
    private Long processCpuTime;
    private Long freePhysicalMemorySize;
    private Long totalPhysicalMemorySize;


    private String requestType;

    public STLBRequest() {
        id = UUID.randomUUID().toString();
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getProcessCpuLoad() {
        return processCpuLoad;
    }

    public void setProcessCpuLoad(Double processCpuLoad) {
        this.processCpuLoad = processCpuLoad;
    }

    public Double getSystemCpuLoad() {
        return systemCpuLoad;
    }

    public void setSystemCpuLoad(Double systemCpuLoad) {
        this.systemCpuLoad = systemCpuLoad;
    }

    public Long getProcessCpuTime() {
        return processCpuTime;
    }

    public void setProcessCpuTime(Long processCpuTime) {
        this.processCpuTime = processCpuTime;
    }

    public Long getFreePhysicalMemorySize() {
        return freePhysicalMemorySize;
    }

    public void setFreePhysicalMemorySize(Long freePhysicalMemorySize) {
        this.freePhysicalMemorySize = freePhysicalMemorySize;
    }

    public Long getTotalPhysicalMemorySize() {
        return totalPhysicalMemorySize;
    }

    public void setTotalPhysicalMemorySize(Long totalPhysicalMemorySize) {
        this.totalPhysicalMemorySize = totalPhysicalMemorySize;
    }
}
