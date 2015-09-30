package com.demosoft.stlb.loadbalancer;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.ConnectException;

/**
 * Created by Andrii_Korkoshko on 17.09.2015.
 */
@Component
public class LoadBalancerHelper {

    private RestTemplate restTemplate = new RestTemplate();


    public Object post(String url, Object request) {
        return restTemplate.<Object>postForEntity(url, request, Object.class);
    }

    public ResponseEntity<String> get(String url) throws ResourceAccessException {
        ResponseEntity<String> response = restTemplate.<String>getForEntity(url, String.class);
        return response;
    }

}
