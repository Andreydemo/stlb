package com.demosoft.stlb.client;

import com.demosoft.stlb.client.bean.SessionTransportBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * Created by andrii_korkoshko on 03.05.16.
 */
@Component
@Scope("session")
public class ReceiveCriticalSessionImp <T> implements ReceiveCriticalSession <T> {

    @Autowired
    HttpSession session;
    @Override
    public SessionTransportBean<T> receive() {
        return new SessionTransportBean<>(session.getId());
    }
}
