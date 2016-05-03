package com.demosoft.stlb.client;

import com.demosoft.stlb.client.bean.SessionTransportBean;

/**
 * Created by andrii_korkoshko on 03.05.16.
 */
public interface ReceiveCriticalSession <T> {

    SessionTransportBean<T> receive();
}
