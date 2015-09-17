package com.demosoft.stlb.bean;

/**
 * Created by Andrii_Korkoshko on 17.09.2015.
 */
public class Node {

    private String url;

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

    @Override
    public String toString() {
        return "Node{" +
                "url='" + url + '\'' +
                '}';
    }
}
