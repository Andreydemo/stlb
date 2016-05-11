package com.demosoft.stlb.client.bean;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by andrii_korkoshko on 03.05.16.
 */
public class SessionTransportBean <T> implements Serializable {

    private T object;

    private Class clazz;

    public SessionTransportBean() {
    }

    public SessionTransportBean(T object) {
        this.object = object;
        clazz = object.getClass();
    }

    public T getObject(){
        return (T) object;
    }

    public Class getSessionClass(){
        return clazz;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public interface SessionTransportBeanFactory<T>{

        SessionTransportBean get(String body) throws IOException;


    }
}
