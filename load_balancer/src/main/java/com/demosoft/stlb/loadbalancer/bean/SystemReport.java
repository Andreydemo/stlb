package com.demosoft.stlb.loadbalancer.bean;

import com.demosoft.stlb.client.bean.STLBRequest;

import java.util.Date;

/**
 * Created by Andrii_Korkoshko on 15.10.2015.
 */
public class SystemReport {

    private Double processCpuLoad;
    private Double systemCpuLoad;
    private Long processCpuTime;
    private Long freePhysicalMemorySize;
    private Long totalPhysicalMemorySize;

    private Date reportDate;


    public SystemReport() {
    }

    public SystemReport(STLBRequest stlbRequest) {
        this.processCpuLoad =((int)(stlbRequest.getProcessCpuLoad() * 10000) / 100.0);
        this.systemCpuLoad = ((int)(stlbRequest.getSystemCpuLoad() * 10000) / 100.0);
        this.processCpuTime = stlbRequest.getProcessCpuTime();
        this.freePhysicalMemorySize = stlbRequest.getFreePhysicalMemorySize() / (1024 * 1024);
        this.totalPhysicalMemorySize = stlbRequest.getTotalPhysicalMemorySize() / (1024 * 1024) ;
        this.reportDate = new Date();
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

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }
}
