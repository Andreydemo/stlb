package com.demosoft.stlb.client.bean;

import java.io.Serializable;
import java.net.URI;
import java.util.UUID;

/**
 * Created by Andrii_Korkoshko on 06.10.2015.
 */
public class STLBInfoRequest implements Serializable {


    private static final long serialVersionUID = 4366077735133337997L;
    private URI loadBalancerURI;
    private String from;
    private String id;
    private String ownNodeId;
    private int interval =  -1;

    public STLBInfoRequest() {
        id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public URI getLoadBalancerURI() {
        return loadBalancerURI;
    }

    public void setLoadBalancerURI(URI loadBalancerURI) {
        this.loadBalancerURI = loadBalancerURI;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
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
}
