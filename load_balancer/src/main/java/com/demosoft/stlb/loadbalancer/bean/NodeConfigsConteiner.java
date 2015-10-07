package com.demosoft.stlb.loadbalancer.bean;

import com.demosoft.stlb.loadbalancer.controller.PerformanceStatisticsReceiver;
import com.demosoft.stlb.loadbalancer.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;
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

    @Value("${defaultBalancerURI}")
    private URI defaultBalancerURI;

    private List<Node> nodes = new ArrayList<>();

    @Autowired
    private AdminService adminService;

    @Autowired
    private PerformanceStatisticsReceiver statisticsReceiver;


    @PostConstruct
    private void init() {
        for (String url : nodesUrls) {
            Node newNode = new Node(url);
            newNode.setBalancerURI(defaultBalancerURI);
            nodes.add(newNode);
        }
        log.info("available Nodes: {}", nodes);
        adminService.updateNodeStatusesWithConnectionToInfo();
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
