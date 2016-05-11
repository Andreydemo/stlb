package com.demosoft.stlb.client;

import com.demosoft.stlb.client.bean.SessionTransportBean;
import com.demosoft.stlb.store.entity.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by andrii_korkoshko on 03.05.16.
 */
@Component
@Scope("session")
public class CriticalSessionSenderImp implements CriticalSessionSender<ShoppingCart> {

    @Autowired
    private SessionBeansContainer beansContainer;

    @Override
    public SessionTransportBean<ShoppingCart>  send()  {
        Map<String,Object> container = new HashMap<>();
        container.put(beansContainer.SHOPPING_CART_TRANSPORT_KEY,beansContainer.getShoppingCart());
        try {
            return new SessionTransportBean<ShoppingCart>((ShoppingCart) beansContainer.getShoppingCart().clone());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
