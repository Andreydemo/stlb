package com.demosoft.stlb.bean;

import java.net.URI;

/**
 * Created by Andrii_Korkoshko on 06.10.2015.
 */
public class STLBInfoRequest {

    private URI loadBalancerURI;


    public URI getLoadBalancerURI() {
        return loadBalancerURI;
    }

    public void setLoadBalancerURI(URI loadBalancerURI) {
        this.loadBalancerURI = loadBalancerURI;
    }
}
