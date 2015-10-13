package com.demosoft.stlb.loadbalancer.dao;

import com.demosoft.stlb.loadbalancer.bean.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Andrii_Korkoshko on 13.10.2015.
 */
@Component
public class NodeDbAccessObject {

    @Autowired
    private MongoOperations mongoOps;

    private static final String NODE_COLLECTION = "nodes";

    public void addNode(Node node) {
        mongoOps.insert(node, NODE_COLLECTION);
    }

    public Node fetchNodeById(String id) {
        return null;
    }

    public Node fetchNodeByUrl(String url) {
        return null;
    }

    public List<Node> fetchNodes() {
        return mongoOps.findAll(Node.class, NODE_COLLECTION);
    }
}
