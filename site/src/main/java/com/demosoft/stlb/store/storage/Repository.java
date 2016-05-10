package com.demosoft.stlb.store.storage;

import com.demosoft.stlb.store.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    public Repository() {
    }

    @PostConstruct
    public void init(){
        List<Product> products = productGenerator.generateList(10);
        for(Product product : products){
            addProduct(product);
        }
    }

    public void addProduct(Product product){
        productsById.put(product.getId(),product);
        productsByName.put(product.getName(),product);
        products.add(product);
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
