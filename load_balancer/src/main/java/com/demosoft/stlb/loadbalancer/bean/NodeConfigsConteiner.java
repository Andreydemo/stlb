package com.demosoft.stlb.loadbalancer.bean;

import com.demosoft.stlb.loadbalancer.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrii_Korkoshko on 17.09.2015.
 */
@Component
public class NodeConfigsConteiner {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("#{'${nodesUrls}'.split(',')}")
    private List<String> nodesUrls = new ArrayList<>();

    private List<Node> nodes = new ArrayList<>();

    @Autowired
    private AdminService adminService;


    @PostConstruct
    private void init() {
        for (String url : nodesUrls) {
            nodes.add(new Node(url));
        }
        log.info("available Nodes: {}", nodes);
        adminService.updateNodeStatuses();
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public List<Node> getAvailbleNodes() {
        List<Node> availbleNodes = new ArrayList<>();
        for (Node node : nodes) {
            if (node.isAvailable()) {
                availbleNodes.add(node);
            }
        }
        return availbleNodes;
    }

    public Node getNodeById(String id){
        for (Node node :nodes){
            if(node.getNodeId().equals(id)){
                return  node;
            }
        }
        return null;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }
}
