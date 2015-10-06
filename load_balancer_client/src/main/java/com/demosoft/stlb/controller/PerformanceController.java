package com.demosoft.stlb.controller;

import com.demosoft.stlb.bean.STLBRequest;
import com.demosoft.stlb.bean.STLBResponse;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import org.springframework.stereotype.Component;

/**
 * Created by Andrii_Korkoshko on 06.10.2015.
 */
@Component
public class PerformanceController {
    private Client client = new Client();
    Kryo kryo = client.getKryo();

    {
        kryo.register(STLBRequest.class);
        kryo.register(STLBResponse.class);
        client.start();
    }
}
