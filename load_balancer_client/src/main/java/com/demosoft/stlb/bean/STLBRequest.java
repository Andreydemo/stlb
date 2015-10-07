package com.demosoft.stlb.bean;

import javax.management.AttributeList;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Andrii_Korkoshko on 06.10.2015.
 */
public class STLBRequest implements Serializable{

    private String id;

    private AttributeList nodeSystemParams;

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

    public AttributeList getNodeSystemParams() {
        return nodeSystemParams;
    }

    public void setNodeSystemParams(AttributeList nodeSystemParams) {
        this.nodeSystemParams = nodeSystemParams;
    }

    public enum RequestType {
        SESSION, NODE
    }
}
