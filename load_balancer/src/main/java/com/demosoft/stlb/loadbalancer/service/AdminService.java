package com.demosoft.stlb.loadbalancer.service;

import com.demosoft.stlb.loadbalancer.LoadBalancerHelper;
import com.demosoft.stlb.loadbalancer.bean.AddNodeBean;
import com.demosoft.stlb.loadbalancer.bean.Node;
import com.demosoft.stlb.loadbalancer.bean.NodeConfigsConteiner;
import com.demosoft.stlb.loadbalancer.controller.NodeInfoConnectionClient;
import com.demosoft.stlb.loadbalancer.dao.NodeDbAccessObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.client.ResourceAccessException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;


/**
 * Created by Andrii_Korkoshko on 30.09.2015.
 */
@Component
public class AdminService {

    @Autowired
    private NodeConfigsConteiner nodeConfigsConteiner;

    @Autowired
    private LoadBalancerHelper loadBalancerHelper;

    @Autowired
    private NodeInfoConnectionClient statisticsReceiver;

    @Autowired
    private NodeDbAccessObject nodeDbAccessObject;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void getAdminPage(Model model) {
        updateNodeStatuses();
        model.addAttribute(AdminPageDataModel.AVAILABLE_NODES, nodeConfigsConteiner.getNodes());
    }

    public void updateNodeStatuses() {
        for (Node node : nodeConfigsConteiner.getNodes()) {
            Thread task = new Thread(new UpdateStatusTask(node));
            log.info("Task for {} created", node.getUrl());
            task.start();
            log.info("Task for {} started", node.getUrl());
        }
    }

    public void updateNodeStatusesWithConnectionToInfo() {
        for (Node node : nodeConfigsConteiner.getNodes()) {
            Thread task = new Thread(new UpdateStatusTaskAndConnEctToInfo(node));
            log.info("Task for {} created", node.getUrl());
            task.start();
            log.info("Task for {} started", node.getUrl());
        }
    }

    public void remodeNode(String url) {
        Node reqestedForDeletion = null;
        synchronized (nodeConfigsConteiner.getNodes()) {
            for (Node node : nodeConfigsConteiner.getNodes()) {
                if (node.getUrl().equalsIgnoreCase(url)) {
                    reqestedForDeletion = node;
                }
            }
            if (reqestedForDeletion != null) {
                nodeConfigsConteiner.getNodes().remove(reqestedForDeletion);
                reqestedForDeletion.setAvailable(false);
            }
        }
    }

    public void addNode(AddNodeBean addNodeBean) {
        System.out.println("Adding node with url:" + addNodeBean.getUrl());
        Node newNode = new Node();
        synchronized (nodeConfigsConteiner.getNodes()) {
            newNode.setUrl(addNodeBean.getUrl().toString());
            newNode.setName(addNodeBean.getName());
            newNode.setBalancerURI(addNodeBean.getBalancerURI());
            nodeConfigsConteiner.getNodes().add(newNode);
        }
        System.out.println("Added node with url:" + addNodeBean.getUrl());
        log.info("Added node with url: {}", addNodeBean.getUrl());
        updateNodeStatuses();
        URI uri = null;
        try {
            uri = new URI(newNode.getUrl());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        nodeDbAccessObject.addNode(newNode);
        newNode.setInfoConnection(statisticsReceiver.connectToNode(newNode.getBalancerURI(), uri));
    }

    class UpdateStatusTask implements Runnable {
        Node node;

        public UpdateStatusTask(Node node) {
            this.node = node;
        }

        @Override
        public void run() {
            try {
                HttpStatus status = loadBalancerHelper.get(node.getUrl()).getStatusCode();
                node.setAvailable(status.is2xxSuccessful());
                if (status.is2xxSuccessful()) {
                    node.setLastAvailible(new Date());
                }
            } catch (ResourceAccessException e) {
                node.setAvailable(false);
            }
            log.info("Task for {} ended", node.getUrl());
        }
    }

    class UpdateStatusTaskAndConnEctToInfo implements Runnable {
        Node node;

        public UpdateStatusTaskAndConnEctToInfo(Node node) {
            this.node = node;
        }

        @Override
        public void run() {
            try {
                HttpStatus status = loadBalancerHelper.get(node.getUrl()).getStatusCode();
                node.setAvailable(status.is2xxSuccessful());
                if (status.is2xxSuccessful()) {
                    node.setLastAvailible(new Date());
                }
            } catch (ResourceAccessException e) {
                node.setAvailable(false);
            }
            log.info("Task for {} ended", node.getUrl());
            if (node.isAvailable()) {
                URI uri = null;
                try {
                    uri = new URI(node.getUrl());
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                node.setInfoConnection(statisticsReceiver.connectToNode(node.getBalancerURI(), uri));
            }
        }
    }
}
