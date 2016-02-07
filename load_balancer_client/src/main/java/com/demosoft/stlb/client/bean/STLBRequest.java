package com.demosoft.stlb.client.bean;

import com.demosoft.stlb.client.collection.SimpleMap;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
    private int interval;
    private String ownNodeId;

    private SimpleMap<String, Double> sessionsLoadings = new SimpleMap<>();


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

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String getOwnNodeId() {
        return ownNodeId;
    }

    public void setOwnNodeId(String ownNodeId) {
        this.ownNodeId = ownNodeId;
    }

    public Map<String, Double> getSessionsLoadings() {
        return sessionsLoadings;
        //return null;
    }

    public void setSessionsLoadings(Map<String, Double> sessionsLoadings) {
        this.sessionsLoadings = (SimpleMap<String, Double>) sessionsLoadings;
    }
}
