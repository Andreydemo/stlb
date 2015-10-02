package com.demosoft.stlb.bean;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Andrii_Korkoshko on 17.09.2015.
 */
public class Node {

    private String name;
    private Date lastAvailible;
    private String nodeId;

    private String url;
    private boolean available = false;
    private int connectionsCount;

    private List<WeakReference<SessionConnection>> connections = new ArrayList<WeakReference<SessionConnection>>();

    public Node() {
        name = "Node";
        nodeId = UUID.randomUUID().toString();
    }

    public Node(String url) {
        this.url = url;
        nodeId = UUID.randomUUID().toString();
        name = "Node - " + url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Node{" +
                "url='" + url + '\'' +
                '}';
    }

    public void addConnection(SessionConnection connection) {
        filterConncections();
        connections.add(new WeakReference<SessionConnection>(connection));
    }

    public int getConnectionCount() {
        filterConncections();
        return connections.size();
    }

    public List<WeakReference<SessionConnection>> getConnections() {
        filterConncections();
        return connections;
    }

    public void filterConncections() {
        List<WeakReference<SessionConnection>> removingList = new ArrayList<>();
        for (WeakReference<SessionConnection> ref : connections) {
            if (ref.get() == null || ref.get().getExpired()) {
                removingList.add(ref);
            }
        }
        connections.removeAll(removingList);
        connectionsCount = connections.size();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getLastAvailible() {
        return lastAvailible;
    }

    public void setLastAvailible(Date lastAvailible) {
        this.lastAvailible = lastAvailible;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public void setConnections(List<WeakReference<SessionConnection>> connections) {
        connectionsCount = connections.size();
        this.connections = connections;
    }

    public int getConnectionsCount() {
        return connectionsCount;
    }

    public void setConnectionsCount(int connectionsCount) {
        this.connectionsCount = connectionsCount;
    }
}
