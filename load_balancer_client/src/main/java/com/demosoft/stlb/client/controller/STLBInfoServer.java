package com.demosoft.stlb.client.controller;

import com.demosoft.stlb.client.bean.STLBInfoRequest;
import com.demosoft.stlb.client.bean.STLBInfoResponse;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

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
        server.start();
        try {
            server.bind(infoPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class STLBInfoServerListener extends Listener {
        public void received(Connection connection, Object object) {
            if (object instanceof STLBInfoRequest) {
                STLBInfoRequest request = (STLBInfoRequest) object;
                try {
                    performanceController.setLoadBalancerURI(request.getLoadBalancerURI());
                    STLBInfoResponse response = new STLBInfoResponse();
                    response.setId(request.getId());
                    response.setSuccess(true);
                    connection.sendTCP(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
