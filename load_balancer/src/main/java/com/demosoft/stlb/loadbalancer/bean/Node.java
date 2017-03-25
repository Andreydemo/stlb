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
    private int infoPort;
    private Double criticalLevel = -1.0;
    private boolean mockUsed = false;
    private MockedNode mockedNode = new MockedNode(this);

    private double maxNodeActivityPoint = 0;
    private double currentNodeActivityPoint = 0;

    private long receivedBites = 0;

    private SessionConnection currentMostActiveSession;

    private List<Long> balancerDelayes = new ArrayList<>();

    private List<WeakReference<SessionConnection>> connections = new ArrayList<WeakReference<SessionConnection>>();
    private long currentAverageBalancerDelay;

    public Node() {
        name = "Node";
        nodeId = UUID.randomUUID().toString();
    }

    public Node(NodeEntity nodeEntity) {
        name = nodeEntity.getName();
        nodeId = nodeEntity.getNodeId();
        lastAvailible = nodeEntity.getLastAvailible();
        infoPort = nodeEntity.getInfoPort();
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
        if (containsConnection(connection)) {
            return;
        }
        WeakReference<SessionConnection> reference = new WeakReference<SessionConnection>(connection);
        connections.add(reference);
    }

    public boolean containsConnection(SessionConnection connection) {
        for (WeakReference<SessionConnection> weakReference : connections) {
            if (weakReference.get() != null && weakReference.get().getNodeJSessionId() != null && weakReference.get().getNodeJSessionId().equalsIgnoreCase(connection.getNodeJSessionId())) {
                return true;
            }
        }
        return false;
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

    public SessionConnection getConnectionByNodeSeesionId(String sessionId) {
        filterConncections();
        for (WeakReference<SessionConnection> connection : connections) {
            if (sessionId.equalsIgnoreCase(connection.get().getNodeJSessionId())) {
                return connection.get();
            }
        }
        return null;
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
        nodeEntity.setInfoPort(infoPort);
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
        synchronized (this) {
            if (systemReports.size() == maxCountSavedSystemReports) {
                systemReports.remove(maxCountSavedSystemReports - 1);
            }
            boolean result = systemReports.add(systemReport);
            Collections.sort(systemReports, SystemReport.defaultComporator);
            calculateNodeActivityPoints();
            currentMostActiveSession = calculateMostActiveSession();
            return result;
        }
    }

    public double getCurrentNodeActivityPoint() {
        return currentNodeActivityPoint;
    }

    public void setCurrentNodeActivityPoint(double currentNodeActivityPoint) {
        this.currentNodeActivityPoint = currentNodeActivityPoint;
    }

    public double getMaxNodeActivityPoint() {
        return maxNodeActivityPoint;
    }

    public void setMaxNodeActivityPoint(double maxNodeActivityPoint) {
        this.maxNodeActivityPoint = maxNodeActivityPoint;
    }

    public Double calculateNodeActivityPoints() {
        synchronized (this) {
            if (mockUsed) {
                return mockedNode.getMockedActivityPoints();
            }
            Double cpuLoad = 0.0;
            Double memoryLoad = 0.0;
            for (SystemReport systemReport : systemReports) {
                cpuLoad += systemReport.getSystemCpuLoad();
                memoryLoad += (systemReport.getTotalPhysicalMemorySize() - systemReport.getFreePhysicalMemorySize())
                        / systemReport.getTotalPhysicalMemorySize();
            }
            cpuLoad /= systemReports.size();
            memoryLoad /= systemReports.size();

            double result = (cpuLoad + memoryLoad) / 2;
            currentNodeActivityPoint = result;
            checkMaxNodeActivityPoint();
            return result;
        }
    }

    private void checkMaxNodeActivityPoint() {
        if (currentNodeActivityPoint > maxNodeActivityPoint) {
            maxNodeActivityPoint = currentNodeActivityPoint;
        }
    }

    public SessionConnection calculateMostActiveSession() {
        if (getStrongConnections().isEmpty()) {
            return null;
        }
        SessionConnection result = getStrongConnections().get(0);
        for (SessionConnection sessionConnection : getStrongConnections()) {
            if (result.getActivity() < sessionConnection.getActivity()) {
                result = sessionConnection;
            }
        }
        currentMostActiveSession = result;
        return result;
    }

    public SessionConnection getCurrentMostActiveSession() {
        return currentMostActiveSession;
    }

    public void setCurrentMostActiveSession(SessionConnection currentMostActiveSession) {
        this.currentMostActiveSession = currentMostActiveSession;
    }

    public int getMaxCountSavedSystemReports() {
        return maxCountSavedSystemReports;
    }

    public void setMaxCountSavedSystemReports(int maxCountSavedSystemReports) {
        this.maxCountSavedSystemReports = maxCountSavedSystemReports;
    }

    public int getInfoPort() {
        return infoPort;
    }

    public void setInfoPort(int infoPort) {
        this.infoPort = infoPort;
    }

    public Double getCriticalLevel() {
        return criticalLevel;
    }

    public void setCriticalLevel(Double criticalLevel) {
        this.criticalLevel = criticalLevel;
    }

    public boolean isMockUsed() {
        return mockUsed;
    }

    public void setMockUsed(boolean mockUsed) {
        this.mockUsed = mockUsed;
    }

    public MockedNode getMockedNode() {
        return mockedNode;
    }

    public void setMockedNode(MockedNode mockedNode) {
        this.mockedNode = mockedNode;
    }

    public void swithcMockStatus() {
        mockUsed = !mockUsed;
    }

    public boolean removeSessionConnection(SessionConnection sessionConnection) {
        boolean result = false;
        List<WeakReference<SessionConnection>> refsToRemove = new ArrayList<>();
        for (WeakReference<SessionConnection> sessionConnectionWeakReference : connections) {
            if (sessionConnectionWeakReference.get() != null && sessionConnectionWeakReference.get().getNodeJSessionId().equalsIgnoreCase(sessionConnection.getNodeJSessionId())) {
                refsToRemove.add(sessionConnectionWeakReference);
            }
        }
        result = connections.removeAll(refsToRemove);
        return result;
    }

    public boolean isInCriticalState() {
        if (criticalLevel.equals(-1.0)) {
            return false;
        }
        return getCurrentNodeActivityPoint() >= criticalLevel;
    }

    public static class CriticalComporator implements Comparator<Node> {

        @Override
        public int compare(Node o1, Node o2) {
            return o1.getCurrentNodeActivityPoint() < o2.getCurrentNodeActivityPoint() ? 1 : 0;
        }
    }

    public void addBites(int bites) {
        receivedBites += bites;
    }

    public long getReceivedBites() {
        return receivedBites;
    }

    public void addBalancerDelay(Long delay) {
        synchronized (this) {
            if (balancerDelayes.size() == maxCountSavedSystemReports) {
                balancerDelayes.remove(maxCountSavedSystemReports - 1);
            }
            balancerDelayes.add(delay);
            calculateCurrentAverageBalancerDelay();
        }
    }

    private void calculateCurrentAverageBalancerDelay() {
        long bufDelay = 0;
        for (Long delay : balancerDelayes) {
            bufDelay += delay;
        }
        currentAverageBalancerDelay = bufDelay / balancerDelayes.size();
    }

    public long getCurrentAverageBalancerDelay() {
        return currentAverageBalancerDelay;
    }

    public void setCurrentAverageBalancerDelay(long currentAverageBalancerDelay) {
        this.currentAverageBalancerDelay = currentAverageBalancerDelay;
    }
}
