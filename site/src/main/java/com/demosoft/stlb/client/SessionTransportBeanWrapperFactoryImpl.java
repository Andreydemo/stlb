package com.demosoft.stlb.client;

import com.demosoft.stlb.client.bean.MyJsonMapper;
import com.demosoft.stlb.client.bean.SessionTransportBean;
import com.demosoft.stlb.store.entity.ShoppingCart;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by andrii_korkoshko on 11.05.16.
 */
@Component
public class SessionTransportBeanWrapperFactoryImpl implements SessionTransportBean.SessionTransportBeanFactory{
    @Autowired
    @Qualifier("jacksonObjectMapper")
    private MyJsonMapper jsonMapper;



    @Override
    public SessionTransportBean get(String body) throws IOException {
        SessionTransportBean sessionTransportBean =  jsonMapper.readValue(body, new TypeReference<SessionTransportBean<ShoppingCart>>() {});
        return sessionTransportBean;
    }

}
