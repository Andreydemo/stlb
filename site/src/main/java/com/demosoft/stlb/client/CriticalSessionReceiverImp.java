package com.demosoft.stlb.client;

import com.demosoft.stlb.client.bean.SessionTransportBean;
import com.demosoft.stlb.store.entity.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by andrii_korkoshko on 03.05.16.
 */
@Component
@Scope("session")
public class CriticalSessionReceiverImp implements CriticalSessionReceiver<ShoppingCart> {

    @Autowired
    private SessionBeansContainer beansContainer;

    @Override
    public boolean receive(SessionTransportBean<ShoppingCart>  sessionTransportBean) {
        ShoppingCart shoppingCart = sessionTransportBean.getObject();
        if(shoppingCart != null) {
            beansContainer.getShoppingCart().apply(shoppingCart);
            return true;
        }
        return false;
    }
}
