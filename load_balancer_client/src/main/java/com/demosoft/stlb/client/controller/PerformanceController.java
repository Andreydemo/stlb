package com.demosoft.stlb.client.controller;

import com.demosoft.stlb.client.bean.STLBInfoRequest;
import com.demosoft.stlb.client.bean.STLBInfoResponse;
import com.demosoft.stlb.client.bean.STLBRequest;
import com.demosoft.stlb.client.bean.STLBResponse;
import com.demosoft.stlb.client.collection.SimpleMap;
import com.demosoft.stlb.client.scheduler.NodeStatisticTask;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.MapSerializer;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
    public static final long timeout = 5000;
    private String ownNodeId;

    @Autowired
    private NodeStatisticTask nodeStatisticTask;

    public PerformanceController() {
        kryo.setRegistrationRequired(false);
        kryo.register(STLBRequest.class);
        kryo.register(STLBResponse.class);
        kryo.register(STLBInfoRequest.class);
        kryo.register(STLBInfoResponse.class);
        kryo.register(SimpleMap.class);
        MapSerializer mapSerializer = new MapSerializer();
        kryo.register(Map.class, mapSerializer);
        kryo.register(HashMap.class, mapSerializer);

        mapSerializer.setKeyClass(String.class, kryo.getSerializer(String.class));
        mapSerializer.setValueClass(Double.class, kryo.getSerializer(Double.class));

        //kryo.register(STLBRequest.RequestType.class);
        kryo.register(URI.class);
        client.addListener(new PerformanceControllerListener());
        System.out.println("PerformanceController - inited correctly");
    }

    @PostConstruct
    public void init() {
    }

    public URI getLoadBalancerURI() {
        return loadBalancerURI;
    }

    private boolean sendRequest(STLBRequest request) {
        if (loadBalancerURI == null) {
            return false;
        }
        try {
            URI uri = new URI(loadBalancerURI.toString());
            System.out.println("try to connect: " + uri);
            client.start();
            client.connect(5000, uri.getHost(), portValidation(uri.getPort()));
            System.out.println("Connected to : " + loadBalancerURI);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        System.out.println("sending status " + client.sendTCP(request) + " request id: " + request.getId());

        System.out.println("request sended to : " + loadBalancerURI);
        STLBResponse stlbResponse = null;
        long time = System.currentTimeMillis();
        while (stlbResponse == null) {
            stlbResponse = lastReponses.remove(request.getId());
            if (System.currentTimeMillis() - time > timeout) {
                System.out.println("Time out : " + loadBalancerURI);
                break;
            }
        }
        if (stlbResponse == null) {
            System.out.println("stlbResponse == null : ");
            return false;
        }
        System.out.println("stlbResponse.isSuccess() : " + stlbResponse.isSuccess());
        return stlbResponse.isSuccess();
    }

    public boolean sendSessionStatistic(STLBRequest request) {
        request.setRequestType(STLBRequest.SESSION);
        return sendRequest(request);
    }


    public boolean sendNodeStatistic(STLBRequest request) {
        request.setRequestType(STLBRequest.NODE);
        return sendRequest(request);
    }

    public void setLoadBalancerURI(URI loadBalancerURI) throws IOException {
        this.loadBalancerURI = loadBalancerURI;
    }

    public static int portValidation(int port) {
        if (port == -1) {
            return 80;
        }
        return port;
    }


    class PerformanceControllerListener extends Listener {
        public void received(Connection connection, Object object) {
            System.out.println("PerformanceControllerListener Response" + object);
            if (object instanceof STLBResponse) {
                STLBResponse response = (STLBResponse) object;
                lastReponses.put(response.getId(), response);
            }
        }
    }

    public String getOwnNodeId() {
        return ownNodeId;
    }

    public void setOwnNodeId(String ownNodeId) {
        this.ownNodeId = ownNodeId;
    }
}
