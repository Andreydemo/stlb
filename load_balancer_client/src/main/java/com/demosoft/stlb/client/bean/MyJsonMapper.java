package com.demosoft.stlb.client.bean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Created by andrii_korkoshko on 11.05.16.
 */
public class MyJsonMapper extends ObjectMapper {

    public MyJsonMapper() {
        this.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }
}