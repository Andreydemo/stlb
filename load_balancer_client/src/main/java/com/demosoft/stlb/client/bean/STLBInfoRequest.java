package com.demosoft.stlb.client.bean;

import java.io.Serializable;
import java.net.URI;
import java.util.UUID;

/**
 * Created by Andrii_Korkoshko on 06.10.2015.
 */
public class STLBInfoRequest implements Serializable {

    private URI loadBalancerURI;
    private String id;

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
}
