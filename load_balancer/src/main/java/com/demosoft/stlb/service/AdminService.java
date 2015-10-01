package com.demosoft.stlb.service;

import com.demosoft.stlb.bean.Node;
import com.demosoft.stlb.bean.NodeConfigsConteiner;
import com.demosoft.stlb.loadbalancer.LoadBalancerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.client.ResourceAccessException;

/**
 * Created by Andrii_Korkoshko on 30.09.2015.
 */
@Component
public class AdminService {

    @Autowired
    private NodeConfigsConteiner nodeConfigsConteiner;

    @Autowired
    private LoadBalancerHelper loadBalancerHelper;

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

    public void addNode(String url) {
        System.out.println("Adding node with url:" + url);
        synchronized (nodeConfigsConteiner.getNodes()) {
            nodeConfigsConteiner.getNodes().add(new Node(url));
        }
        System.out.println("Added node with url:" + url);
        updateNodeStatuses();
        System.out.println("statuses updated");
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
            } catch (ResourceAccessException e) {
                node.setAvailable(false);
            }
            log.info("Task for {} ended", node.getUrl());
        }
    }
}
