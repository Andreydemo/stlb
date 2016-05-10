package com.demosoft.stlb.store.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * Created by andrii_korkoshko on 10.05.16.
 */
@Component
@Scope(value="session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ShoppingCart {

    private Order currentOrder = new Order();

    public void clearOrder(){
        currentOrder = new Order();
    }

    public void addProduct(Product product){
        currentOrder.addProduct(product);
    }
    public void addProduct(Product product,Integer quantity){
        currentOrder.addProduct(product,quantity);
    }

    public Order getOrder(){
        return currentOrder;
    }
}
