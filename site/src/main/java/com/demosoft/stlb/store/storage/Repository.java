package com.demosoft.stlb.store.storage;

import com.demosoft.stlb.client.controller.ProductSynchronizationService;
import com.demosoft.stlb.store.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by andrii_korkoshko on 10.05.16.
 */
@Component
public class Repository {

    private Map<String,Product> productsById = new HashMap<>();
    private Map<String,Product> productsByName = new HashMap<>();
    private List<Product> products = new ArrayList<>();

    @Autowired
    private RandomProductGenerator productGenerator;

    @Autowired
    private ProductSynchronizationService productSynchronizationService;


    public Repository() {
    }

    @PostConstruct
    public void init(){
        if(!productSynchronizationService.isSync()) {
            List<Product> products = productGenerator.generateList(10);
            for (Product product : products) {
                addProduct(product);
            }
        } else {
            productSynchronizationService.sync();
        }
    }

    public void addProduct(Product product){
        productsById.put(product.getId(),product);
        productsByName.put(product.getName(),product);
        products.add(product);
    }

    public void addProducts(Collection<Product> products){
        for (Product product : products){
            addProduct(product);
        }
    }
    public Product getProductById(String id){
        return productsById.get(id);
    }
    public void getProductByName(String name){
        productsByName.get(name);
    }


    public List<Product> getAllProducts(){
        return  new ArrayList<>(products);
    }


}
