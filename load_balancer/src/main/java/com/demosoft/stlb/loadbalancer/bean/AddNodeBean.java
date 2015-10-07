package com.demosoft.stlb.loadbalancer.bean;

import java.net.URI;

/**
 * Created by Andrii_Korkoshko on 07.10.2015.
 */
public class AddNodeBean {
    private String name;
    private URI url;
    private URI balancerURI;

    public URI getBalancerURI() {
        return balancerURI;
    }

    public void setBalancerURI(URI balancerURI) {
        this.balancerURI = balancerURI;
    }

    public URI getUrl() {
        return url;
    }

    public void setUrl(URI url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
