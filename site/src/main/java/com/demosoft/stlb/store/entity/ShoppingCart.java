package com.demosoft.stlb.store.entity;

import com.demosoft.stlb.client.Applyble;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by andrii_korkoshko on 10.05.16.
 */
@Component
@Scope(value="session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ShoppingCart implements Applyble<ShoppingCart>,Serializable,Cloneable{

    protected Order currentOrder = new Order();

    public void clearOrder(){
        currentOrder = new Order();
    }

    public ShoppingCart() {
    }

    public ShoppingCart (ShoppingCart shoppingCart){
        currentOrder =  shoppingCart.getOrder();
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

    @Override
    public ShoppingCart clone() throws CloneNotSupportedException {
        return new ShoppingCart(this);
    }



    public void setOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }

    @Override
    public void apply(ShoppingCart object) {
        currentOrder = object.getOrder();
    }
}
