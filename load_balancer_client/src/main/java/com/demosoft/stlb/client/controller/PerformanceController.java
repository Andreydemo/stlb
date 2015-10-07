package com.demosoft.stlb.client.controller;

import com.demosoft.stlb.client.bean.STLBRequest;
import com.demosoft.stlb.client.bean.STLBResponse;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andrii_Korkoshko on 06.10.2015.
 */
@Component
public class PerformanceController {
    private URI loadBalancerURI;
    private Client client = new Client();
    private Kryo kryo = client.getKryo();
    private Map<String, STLBResponse> lastReponses = new HashMap<>();
    public static final long timeout = 1000;

    public PerformanceController() {
        kryo.register(STLBRequest.class);
        kryo.register(STLBResponse.class);
        client.start();
        client.addListener(new PerformanceControllerListener());

    }

    public URI getLoadBalancerURI() {
        return loadBalancerURI;
    }

    private boolean sendRequest(STLBRequest request) {
        if (loadBalancerURI == null) {
            return false;
        }
        client.sendTCP(request);
        STLBResponse stlbResponse = null;
        long time = System.currentTimeMillis();
        while (stlbResponse == null) {
            stlbResponse = lastReponses.remove(request.getId());
            if (System.currentTimeMillis() - time > timeout) {
                break;
            }
        }
        if (stlbResponse == null) {
            return false;
        }
        return stlbResponse.isSuccess();
    }

    public boolean sendSessionStatistic(STLBRequest request) {
        request.setRequestType(STLBRequest.RequestType.SESSION);
        return sendRequest(request);
    }


    public boolean sendNodeStatistic(STLBRequest request) {
        request.setRequestType(STLBRequest.RequestType.NODE);
        return sendRequest(request);
    }

    public void setLoadBalancerURI(URI loadBalancerURI) throws IOException {
        this.loadBalancerURI = loadBalancerURI;
        client.connect(5000, loadBalancerURI.getHost(), loadBalancerURI.getPort());
    }

    class PerformanceControllerListener extends Listener {
        public void received(Connection connection, Object object) {
            if (object instanceof STLBResponse) {
                STLBResponse response = (STLBResponse) object;
                lastReponses.put(response.getId(), response);
            }
        }
    }
}
