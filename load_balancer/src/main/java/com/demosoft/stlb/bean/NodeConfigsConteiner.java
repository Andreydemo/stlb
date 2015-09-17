package com.demosoft.stlb.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private List<Node> availableNodes = new ArrayList<>();


    @PostConstruct
    private void init() {
        for (String url : nodesUrls) {
            availableNodes.add(new Node(url));
        }
        log.info("available Nodes: {}",availableNodes);
    }
}
