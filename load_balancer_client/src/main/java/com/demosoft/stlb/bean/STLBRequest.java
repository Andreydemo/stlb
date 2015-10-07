package com.demosoft.stlb.bean;

import java.util.UUID;

/**
 * Created by Andrii_Korkoshko on 06.10.2015.
 */
public class STLBRequest {

    private String id;

    public enum RequestType {
        SESSION, NODE
    }

    private RequestType requestType;

    public STLBRequest() {
        id = UUID.randomUUID().toString();
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
