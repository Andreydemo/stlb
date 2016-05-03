package com.demosoft.stlb.client;

import com.demosoft.stlb.client.bean.SessionTransportBean;
import org.springframework.stereotype.Component;

/**
 * Created by andrii_korkoshko on 03.05.16.
 */
@Component
public class ReceiveCriticalSessionImp <T> implements ReceiveCriticalSession <T> {
    @Override
    public SessionTransportBean<T> receive() {
        return null;
    }
}
