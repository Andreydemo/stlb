package com.demosoft.stlb.bean;

import java.io.Serializable;

/**
 * Created by Andrii_Korkoshko on 06.10.2015.
 */
public class STLBResponse implements Serializable {

    private String id;

    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
