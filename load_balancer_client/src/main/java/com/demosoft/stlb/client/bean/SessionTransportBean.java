package com.demosoft.stlb.client.bean;

/**
 * Created by andrii_korkoshko on 03.05.16.
 */
public class SessionTransportBean <T> {

    private Object object;

    private Class clazz;

    public SessionTransportBean(Object object) {
        this.object = object;
        clazz = object.getClass();
    }

    public T getObject(){
        return (T) object;
    }

    public Class getSessionClass(){
        return clazz;
    }
}
