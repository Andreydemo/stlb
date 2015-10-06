package com.demosoft.stlb.controller;

import com.demosoft.stlb.bean.STLBInfoRequest;
import com.demosoft.stlb.bean.STLBInfoResponse;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by Andrii_Korkoshko on 06.10.2015.
 */
@Component
public class STLBInfoServer {
    public static final int infoPort = 54555;
    private Server server = new Server();
    Kryo kryo = server.getKryo();

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
}
