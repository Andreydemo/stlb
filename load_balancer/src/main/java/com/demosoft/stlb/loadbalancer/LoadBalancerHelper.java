package com.demosoft.stlb.loadbalancer;

import com.demosoft.stlb.bean.Node;
import com.demosoft.stlb.bean.NodeConfigsConteiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Andrii_Korkoshko on 17.09.2015.
 */
@Component
public class LoadBalancerHelper {

    @Autowired
    private NodeConfigsConteiner nodeConfigsConteiner;

    private RestTemplate restTemplate = new RestTemplate();


    public Object post(String url, Object request) {
        return restTemplate.<Object>postForEntity(url, request, Object.class);
    }

    public ResponseEntity<String> get(String url) throws ResourceAccessException {
        ResponseEntity<String> response = restTemplate.<String>getForEntity(url, String.class);
        return response;
    }

    public Node getAvailibleNode() {
        return nodeConfigsConteiner.getAvailbleNodes().get(0);
    }

}
