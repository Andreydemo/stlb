package com.demosoft.stlb.loadbalancer.bean;

import com.demosoft.stlb.loadbalancer.controller.NodeInfoConnectionClient;
import com.demosoft.stlb.loadbalancer.dao.NodeDbAccessObject;
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

    @Value("${defaultBalancerPort}")
    private int defaultBalancerPort;

    private List<Node> nodes = new ArrayList<>();

    @Autowired
    private AdminService adminService;

    @Autowired
    private NodeInfoConnectionClient statisticsReceiver;
    @Autowired
    private NodeDbAccessObject nodeDbAccessObject;

    @PostConstruct
    private void init() {
        List<Node> dbNodes = nodeDbAccessObject.fetchNodes();
        if (dbNodes == null || dbNodes.size() == 0) {

            for (String url : nodesUrls) {
                Node newNode = new Node(url);
                newNode.setBalancerURI(compileDefaultBalancerURI(defaultBalancerURI));
                nodes.add(newNode);
            }
        } else {
            nodes.addAll(dbNodes);
        }
        log.info("available Nodes: {}", nodes);
        adminService.updateNodeStatusesWithConnectionToInfo();
    }

    private URI compileDefaultBalancerURI(URI defaultBalancerURI) {

        try {
            return new URI(defaultBalancerURI.getScheme(), defaultBalancerURI.getUserInfo(), defaultBalancerURI.getHost(), defaultBalancerPort, defaultBalancerURI.getPath(), defaultBalancerURI.getQuery(), defaultBalancerURI.getFragment());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return defaultBalancerURI;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public List<Node> getAvailbleNodes() {
        List<Node> availbleNodes = new ArrayList<>();
        for (Node node : nodes) {
            if (node.isCanBeUsed()) {
                availbleNodes.add(node);
            }
        }
        return availbleNodes;
    }

    public Node getNodeById(String id) {
        for (Node node : nodes) {
            if (node.getNodeId().equals(id)) {
                return node;
            }
        }
        return null;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public int getDefaultBalancerPort() {
        return defaultBalancerPort;
    }
}
