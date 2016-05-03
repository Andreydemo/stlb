package com.demosoft.stlb.loadbalancer.bean;

import java.util.Date;

/**
 * Created by Andrii_Korkoshko on 13.10.2015.
 */
public class NodeEntity {

    private String name;
    private Date lastAvailible;
    private String nodeId;
    private String url;
    private String balancerURI;
    private int infoPort;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getLastAvailible() {
        return lastAvailible;
    }

    public void setLastAvailible(Date lastAvailible) {
        this.lastAvailible = lastAvailible;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBalancerURI() {
        return balancerURI;
    }

    public void setBalancerURI(String balancerURI) {
        this.balancerURI = balancerURI;
    }

    public int getInfoPort() {
        return infoPort;
    }

    public void setInfoPort(int infoPort) {
        this.infoPort = infoPort;
    }
}
