package com.demosoft.stlb.bean;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrii_Korkoshko on 17.09.2015.
 */
public class Node {

    private String url;
    private boolean available = false;

    private List<WeakReference<SessionConnection>> connections = new ArrayList<WeakReference<SessionConnection>>();

    public Node() {
    }

    public Node(String url) {
        this.url = url;
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

    public int getConnectionCount(){
        filterConncections();
       return connections.size();
    }
    public List<WeakReference<SessionConnection>> getConnections(){
        filterConncections();
        return connections;
    }

    public void filterConncections(){
        List<WeakReference<SessionConnection>> removingList = new ArrayList<>();
        for (WeakReference<SessionConnection> ref : connections){
            if(ref.get() == null || ref.get().getExpired()){
                removingList.add(ref);
            }
        }
        connections.removeAll(removingList);
    }
}
