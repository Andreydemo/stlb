package com.demosoft.stlb.store;

import com.demosoft.stlb.store.entity.Product;
import com.demosoft.stlb.store.storage.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by andrii_korkoshko on 10.05.16.
 */
@Component
public class ProductService {


    @Autowired
    private Repository repository;




    public List<Product> getAllProducts(){
        return  repository.getAllProducts();
    }

    public Product getProductById(String id){
        return repository.getProductById(id);
    }
}
