package com.demosoft.stlb.loadbalancer;

import com.demosoft.stlb.bean.Node;
import com.demosoft.stlb.bean.NodeConfigsConteiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Andrii_Korkoshko on 17.09.2015.
 */
@Component
public class LoadBalancerHelper {

    @Autowired
    private NodeConfigsConteiner nodeConfigsConteiner;

    private RestTemplate restTemplate = new RestTemplate();

    private List<Node> usedNodes = new ArrayList<>();


    public Object post(String url, Object request) {
        return restTemplate.<Object>postForEntity(url, request, Object.class);
    }

    public ResponseEntity<String> get(String url) throws ResourceAccessException {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
        headers.setContentType(MediaType.TEXT_HTML);
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response;
    }

    public Node getAvailibleNode() {
        List<Node> nodes = nodeConfigsConteiner.getAvailbleNodes();
        if (nodes.size() == 0) {
            return null;
        }
        nodes.removeAll(usedNodes);
        if (nodes.size() == 0) {
            nodes = usedNodes;
            usedNodes = new ArrayList<>();
        }
        usedNodes.add(nodes.get(0));
        return nodes.get(0);
    }

}
