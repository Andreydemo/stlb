package com.demosoft.stlb.client;

import com.demosoft.stlb.client.bean.SessionTransportBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by andrii_korkoshko on 03.05.16.
 */
@Component
@Scope("session")
public class SendCriticalSessionImp <T> implements SendCriticalSession <T> {
    @Override
    public boolean send(SessionTransportBean<T> sessionTransportBean) {
        return false;
    }
}
