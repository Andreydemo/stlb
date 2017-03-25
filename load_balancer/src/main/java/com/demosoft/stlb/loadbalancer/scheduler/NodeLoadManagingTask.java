package com.demosoft.stlb.loadbalancer.scheduler;

import com.demosoft.stlb.loadbalancer.LoadBalancerHelper;
import com.demosoft.stlb.loadbalancer.bean.Node;
import com.demosoft.stlb.loadbalancer.bean.NodeConfigsConteiner;
import com.demosoft.stlb.loadbalancer.bean.SessionConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by Andrii_Korkoshko on 07.10.2015.
 */
@Component
public class NodeLoadManagingTask {





    @Autowired
    private NodeConfigsConteiner nodeConfigsConteiner;

    @Autowired
    private LoadBalancerHelper loadBalancerHelper;

    public NodeLoadManagingTask() {

    }

    public void execute() {

       List<Node> criticalNodes =  loadBalancerHelper.getNodesInCriticalState();
        if(criticalNodes == null || criticalNodes.isEmpty()){
            return;
        }
        List<Node> normalNodes = loadBalancerHelper.getNodeInNormalState();
        if(normalNodes == null || normalNodes.isEmpty()){
            return;
        }
        Collections.sort(criticalNodes,new Node.CriticalComporator());

        Collections.sort(normalNodes,Collections.reverseOrder(new Node.CriticalComporator()));

        Iterator<Node> normalNodesIterator = normalNodes.iterator();
        for(Node criticalNode : criticalNodes){
            if(!normalNodesIterator.hasNext()){
                normalNodesIterator  = normalNodes.iterator();
            }
            SessionConnection sessionConnection = criticalNode.calculateMostActiveSession();
            if(sessionConnection != null){
                loadBalancerHelper.reassignSessionForNode(sessionConnection,normalNodesIterator.next());
            }
        }


    }

}
