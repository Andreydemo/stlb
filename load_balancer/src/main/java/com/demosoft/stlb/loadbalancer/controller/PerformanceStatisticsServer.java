package com.demosoft.stlb.loadbalancer.controller;

import com.demosoft.stlb.client.bean.STLBInfoRequest;
import com.demosoft.stlb.client.bean.STLBInfoResponse;
import com.demosoft.stlb.client.bean.STLBRequest;
import com.demosoft.stlb.client.bean.STLBResponse;
import com.demosoft.stlb.loadbalancer.bean.Node;
import com.demosoft.stlb.loadbalancer.bean.NodeConfigsConteiner;
import com.demosoft.stlb.loadbalancer.bean.SystemReport;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andrii_Korkoshko on 08.10.2015.
 */
@Component
public class PerformanceStatisticsServer {

    @Autowired
    private NodeConfigsConteiner nodeConfigsConteiner;


    public static final int infoPort = 55555;

    private Server server = new Server();

    private Kryo kryo = server.getKryo();

    public PerformanceStatisticsServer() {
        kryo.register(STLBRequest.class);
        kryo.register(STLBResponse.class);
        kryo.register(STLBInfoRequest.class);
        kryo.register(STLBInfoResponse.class);
        kryo.register(URI.class);
        kryo.register(Map.class);
        kryo.register(HashMap.class);
        server.start();
        server.addListener(new NodeStatisticListener());

    }

    @PostConstruct
    public void init() {
        try {
            server.bind(infoPort);
            System.out.println("Info port: " + nodeConfigsConteiner.getDefaultBalancerPort() + " binded");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class NodeStatisticListener extends Listener {
        public void received(Connection connection, Object object) {
            System.out.println("NodeStatisticListener" + object);
            if (object instanceof STLBRequest) {
                System.out.println("NodeStatisticListener");
                System.out.println("object" + object);
                STLBRequest request = (STLBRequest) object;
                System.out.println(request.getSystemCpuLoad());
                STLBResponse response = new STLBResponse();
                response.setSuccess(true);
                response.setId(request.getId());
                connection.sendTCP(response);
                Node node = nodeConfigsConteiner.getNodeById(request.getOwnNodeId());
                node.setInterval(request.getInterval());
                node.addSystemReport(new SystemReport(request));
            }
        }

    }
}
