package com.demosoft.stlb.loadbalancer.bean;

/**
 * Created by andrii_korkoshko on 06.05.16.
 */
public class MockedNode {

    private Double activityPointsPerSession;
    private Node node;

    public MockedNode(Node node) {
        this.node = node;
    }

    public Double getMockedActivityPoints(){
        return node.getStrongConnections().size() * activityPointsPerSession;
    }

    public Double getActivityPointsPerSession() {
        return activityPointsPerSession;
    }

    public void setActivityPointsPerSession(Double activityPointsPerSession) {
        this.activityPointsPerSession = activityPointsPerSession;
    }
}
