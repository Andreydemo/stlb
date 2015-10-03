package com.demosoft.stlb.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Andrii_Korkoshko on 17.09.2015.
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionConnection {

    @Autowired
    private SessionRegistry sessionRegistry;

    private Node node;
    private String jSessionId;
    private String nodeJSessionId;
    private Boolean expired = false;
    private Date lastActivity;

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
        node.addConnection(this);
    }

    public String getjSessionId() {
        return jSessionId;
    }

    public void setjSessionId(String jSessionId) {
        sessionRegistry.put(jSessionId, this);
        this.jSessionId = jSessionId;
    }

    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    public String getNodeJSessionId() {
        return nodeJSessionId;
    }

    public void setNodeJSessionId(String nodeJSessionId) {
        this.nodeJSessionId = nodeJSessionId;
    }

    public Date getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(Date lastActivity) {
        this.lastActivity = lastActivity;
    }

    public void updateActivity(){
        this.lastActivity = new Date();
    }
}
