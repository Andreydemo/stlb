package com.demosoft.stlb.client.controller;

import com.demosoft.stlb.client.bean.STLBInfoRequest;
import com.demosoft.stlb.client.bean.STLBInfoResponse;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.management.AttributeList;
import java.io.IOException;
import java.net.URI;

/**
 * Created by Andrii_Korkoshko on 06.10.2015.
 */
@Component
public class STLBInfoServer {

    public static final int infoPort = 54555;

    @Autowired
    private PerformanceController performanceController;

    private Server server = new Server();

    private Kryo kryo = server.getKryo();

    public STLBInfoServer() {
        kryo.register(STLBInfoRequest.class);
        kryo.register(STLBInfoResponse.class);
        kryo.register(URI.class);
        kryo.register(AttributeList.class);
        server.start();
        try {
            server.bind(infoPort);
            System.out.println("Info port: " + infoPort + " binded");
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.addListener(new STLBInfoServerListener());
    }

    class STLBInfoServerListener extends Listener {
        public void received(Connection connection, Object object) {
            if (object instanceof STLBInfoRequest) {
                STLBInfoRequest request = (STLBInfoRequest) object;
                try {
                    System.out.println("->>> from: " + request.getFrom());
                    performanceController.setLoadBalancerURI(request.getLoadBalancerURI());
                    STLBInfoResponse response = new STLBInfoResponse();
                    response.setId(request.getId());
                    response.setSuccess(true);
                    response.setFrom("STLB info server ");
                    System.out.println("sending response");
                    connection.sendTCP(response);
                    System.out.println("response sended");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
