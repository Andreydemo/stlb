package com.demosoft.stlb.service;

import com.demosoft.stlb.bean.Node;
import com.demosoft.stlb.bean.NodeConfigsConteiner;
import com.demosoft.stlb.loadbalancer.LoadBalancerHelper;
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

    public void getAdminPage(Model model) {
        updateNodeStatuses();
        model.addAttribute(AdminPageDataModel.AVAILABLE_NODES, nodeConfigsConteiner.getAvailableNodes());
    }

    private void updateNodeStatuses() {
        for (Node node : nodeConfigsConteiner.getAvailableNodes()) {
            try {
                synchronized (nodeConfigsConteiner.getAvailableNodes()) {
                    HttpStatus status = loadBalancerHelper.get(node.getUrl()).getStatusCode();
                    node.setAvailable(status.is2xxSuccessful());
                }
            } catch (ResourceAccessException e) {
                node.setAvailable(false);
            }
        }
    }

    public void remodeNode(String url) {
        Node reqestedForDeletion = null;
        synchronized (nodeConfigsConteiner.getAvailableNodes()) {
            for (Node node : nodeConfigsConteiner.getAvailableNodes()) {
                if (node.getUrl().equalsIgnoreCase(url)) {
                    reqestedForDeletion = node;
                }
            }
            if (reqestedForDeletion != null) {
                nodeConfigsConteiner.getAvailableNodes().remove(reqestedForDeletion);
            }
        }
    }

    public void addNode(String url) {
        synchronized (nodeConfigsConteiner.getAvailableNodes()) {
            nodeConfigsConteiner.getAvailableNodes().add(new Node(url));
        }
        updateNodeStatuses();
    }
}
