package com.demosoft.stlb.client.bean;

import java.io.Serializable;

/**
 * Created by Andrii_Korkoshko on 06.10.2015.
 */
public class STLBInfoResponse implements Serializable {

    private String id;

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
}
