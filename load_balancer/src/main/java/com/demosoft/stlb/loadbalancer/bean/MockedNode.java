package com.demosoft.stlb.loadbalancer.bean;

/**
 * Created by andrii_korkoshko on 06.05.16.
 */
public class MockedNode {

    private Double activityPointsPerSession = 0.0;
    private Node node;

    public MockedNode(Node node) {
        this.node = node;
    }

    public Double getMockedActivityPoints(){
        Double points = 0.0;

        if(node.getStrongConnections() != null && !node
                .getStrongConnections().isEmpty()){
            points =  node.getStrongConnections().size() * activityPointsPerSession;
        }
        return  points;
    }

    public Double getActivityPointsPerSession() {
        return activityPointsPerSession;
    }

    public void setActivityPointsPerSession(Double activityPointsPerSession) {
        this.activityPointsPerSession = activityPointsPerSession;
    }
}
