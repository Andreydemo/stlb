package com.demosoft.stlb.loadbalancer.controller;

import com.demosoft.stlb.client.bean.STLBInfoRequest;
import com.demosoft.stlb.client.bean.STLBInfoResponse;
import com.demosoft.stlb.client.controller.STLBInfoServer;
import com.demosoft.stlb.loadbalancer.bean.NodeConfigsConteiner;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andrii_Korkoshko on 07.10.2015.
 */
@Component
public class NodeInfoConnectionClient {
    private Client client = new Client();
    private Kryo clientKryo = client.getKryo();
    private Map<String, STLBInfoResponse> lastReponses = new HashMap<>();
    public static final long timeout = 1000;
    public static final int infoPort = 55555;

    @Autowired
    private NodeConfigsConteiner nodeConfigsConteiner;

    public NodeInfoConnectionClient() {
        clientKryo.register(STLBInfoRequest.class);
        clientKryo.register(STLBInfoResponse.class);
        clientKryo.register(URI.class).getId();
        client.addListener(new NodeConncetionListener());
    }


    public boolean connectToNode(URI loadBalancerURI, URI nodeURI) {
        STLBInfoRequest request = new STLBInfoRequest();
        request.setLoadBalancerURI(loadBalancerURI);
        request.setFrom("Load balancer");
        try {
            client.start();
            System.out.println("connection to: " + nodeURI.getHost() + ":" + STLBInfoServer.infoPort);
            client.connect(5000, nodeURI.getHost(), STLBInfoServer.infoPort);
            System.out.println("connected to: " + nodeURI.getHost() + ":" + STLBInfoServer.infoPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
        client.sendTCP(request);
        STLBInfoResponse stlbResponse = null;
        long time = System.currentTimeMillis();
        while (stlbResponse == null) {
            stlbResponse = lastReponses.remove(request.getId());
            if (System.currentTimeMillis() - time > timeout) {
                System.out.println("time out for " + nodeURI.getHost());
                break;
            }
        }
        if (stlbResponse == null) {
            System.out.println("stlbResponse = null");
            return false;
        }
        System.out.println("stlbResponse.isSuccess() " + stlbResponse.isSuccess());
        return stlbResponse.isSuccess();
    }

    class NodeConncetionListener extends Listener {
        public void received(Connection connection, Object object) {
            if (object instanceof STLBInfoResponse) {
                STLBInfoResponse response = (STLBInfoResponse) object;
                lastReponses.put(response.getId(), response);
            }
        }
    }
}
