package com.demosoft.stlb.client.bean;

import java.io.Serializable;

/**
 * Created by Andrii_Korkoshko on 06.10.2015.
 */
public class STLBInfoResponse implements Serializable {

    private static final long serialVersionUID = 991925589261798626L;
    private String id;
    private String ownNodeId;

    private String from;

    boolean success;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getOwnNodeId() {
        return ownNodeId;
    }

    public void setOwnNodeId(String ownNodeId) {
        this.ownNodeId = ownNodeId;
    }
}
