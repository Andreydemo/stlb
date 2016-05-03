package com.demosoft.stlb.loadbalancer;

import com.demosoft.stlb.client.bean.STLBRequest;
import com.demosoft.stlb.loadbalancer.bean.Node;
import com.demosoft.stlb.loadbalancer.bean.SessionConnection;
import com.demosoft.stlb.loadbalancer.bean.SessionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by Andrii_Korkoshko on 17.09.2015.
 */
@Component
public class SessionUtils {


    @Autowired
    private SessionRegistry sessionRegistry;


    public void registerActivity(Node node, STLBRequest request) {
        System.out.println("---start session statistic registration");
        Map<String, Double> sessionsLoadings = request.getSessionsLoadings();
        System.out.println("------session loaddings" + sessionsLoadings);
        if (sessionsLoadings != null) {
            for(Map.Entry<String,Double> load : sessionsLoadings.entrySet()){
        System.out.println("------session loaddings: id: " + load.getKey() + " load: " + load.getValue());
                SessionConnection sessionConnection =  node.getConnectionByNodeSeesionId(load.getKey());
                if(sessionConnection != null){
                    sessionConnection.setActivity(load.getValue());
                    System.out.println("registered statistic: " + load.getKey() + " : " + load.getValue());
                }
            }
        }
    }

}
