package com.demosoft.stlb.bean;

/**
 * Created by Andrii_Korkoshko on 30.09.2015.
 */
public class ConfigChangingBean {

    private String type;
    private String name;
    private Object value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ConfigChangingBean{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
