package com.demosoft.stlb.client;

import com.demosoft.stlb.store.entity.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by andrii_korkoshko on 11.05.16.
 */
@Component
@Scope("session")
public class SessionBeansContainer {

    @Autowired
    private ShoppingCart shoppingCart;

    public static final String SHOPPING_CART_TRANSPORT_KEY = "shoppingCart";


    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }
}
