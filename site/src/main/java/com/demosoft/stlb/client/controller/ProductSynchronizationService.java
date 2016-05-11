package com.demosoft.stlb.client.controller;

import com.demosoft.stlb.client.bean.MyJsonMapper;
import com.demosoft.stlb.store.entity.Product;
import com.demosoft.stlb.store.storage.Repository;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by andrii_korkoshko on 11.05.16.
 */
@Component
public class ProductSynchronizationService {

    @Autowired
    private Repository repository;

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    @Qualifier("jacksonObjectMapper")
    private MyJsonMapper jsonMapper;

    @Value("${productURL}")
    private String url;

    @Value("${syncProducts}")
    private boolean sync;





    public void sync(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        try {
            List<Product> products = jsonMapper.readValue(response.getBody(), new TypeReference<List<Product>>() {});
            repository.addProducts(products);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isSync() {
        return sync;
    }

    public void setSync(boolean sync) {
        this.sync = sync;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
