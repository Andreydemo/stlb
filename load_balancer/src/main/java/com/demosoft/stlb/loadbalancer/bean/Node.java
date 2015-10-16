package com.demosoft.stlb.loadbalancer.bean;

import java.lang.ref.WeakReference;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Created by Andrii_Korkoshko on 17.09.2015.
 */
public class Node {

    private String name;
    private Date lastAvailible;
    private String nodeId;

    private String url;
    private boolean available = false;
    private boolean infoConnection = false;
    private int connectionsCount;
    private URI balancerURI;
    private boolean enabled = true;
    private int interval = -1;
    private List<SystemReport> systemReports = new ArrayList<>();
    private int maxCountSavedSystemReports = 20;


    private List<WeakReference<SessionConnection>> connections = new ArrayList<WeakReference<SessionConnection>>();

    public Node() {
        name = "Node";
        nodeId = UUID.randomUUID().toString();
    }

    public Node(NodeEntity nodeEntity) {
        name = nodeEntity.getName();
        nodeId = nodeEntity.getNodeId();
        lastAvailible = nodeEntity.getLastAvailible();
        url = nodeEntity.getUrl();
        try {
            balancerURI = new URI(nodeEntity.getBalancerURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public Node(String url) {
        this.url = url;
        nodeId = UUID.randomUUID().toString();
        name = "Node - " + nodeId;
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

    public List<SessionConnection> getStrongConnections() {
        filterConncections();
        List<SessionConnection> connections = new ArrayList<>();
        for (WeakReference<SessionConnection> weak : this.connections) {
            connections.add(weak.get());
        }
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

    public URI getBalancerURI() {
        return balancerURI;
    }

    public void setBalancerURI(URI balancerURI) {
        this.balancerURI = balancerURI;
    }

    public boolean isInfoConnection() {
        return infoConnection;
    }

    public void setInfoConnection(boolean infoConnection) {
        this.infoConnection = infoConnection;
    }

    public NodeEntity getNodeEntity() {
        NodeEntity nodeEntity = new NodeEntity();
        nodeEntity.setBalancerURI(balancerURI.toString());
        nodeEntity.setLastAvailible(lastAvailible);
        nodeEntity.setName(name);
        nodeEntity.setNodeId(nodeId);
        nodeEntity.setUrl(url);
        return nodeEntity;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isCanBeUsed() {
        return available & enabled;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public List<SystemReport> getSystemReports() {
        return systemReports;
    }

    public void setSystemReports(List<SystemReport> systemReports) {
        this.systemReports = systemReports;
    }

    public boolean addSystemReport(SystemReport systemReport) {
        if(systemReports.size() == maxCountSavedSystemReports){
            systemReports.remove(maxCountSavedSystemReports -1 );
        }
        boolean result = systemReports.add(systemReport);
        Collections.sort(systemReports, SystemReport.defaultComporator);
        return result;
    }

    public int getMaxCountSavedSystemReports() {
        return maxCountSavedSystemReports;
    }

    public void setMaxCountSavedSystemReports(int maxCountSavedSystemReports) {
        this.maxCountSavedSystemReports = maxCountSavedSystemReports;
    }


}
