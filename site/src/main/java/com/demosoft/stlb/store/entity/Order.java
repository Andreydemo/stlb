package com.demosoft.stlb.store.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by andrii_korkoshko on 10.05.16.
 */
public class Order {

    private Map<Product,Integer> products = new HashMap<>();
    private Integer total = 0;
    private Integer itemsCount = 0;
    private Integer productsCount = 0;

    public void addProduct(Product product){
        addProduct(product,1);
        reprice();
    }

    public void addProduct (Product product, Integer newQuantity){
        Integer quantity = products.get(product);
        if(quantity == null || quantity.equals(0)){
            products.put(product,new Integer(newQuantity));
        } else {
            quantity += newQuantity;
            products.put(product,new Integer(quantity));
        }
        reprice();
    }

    public void reprice(){
        total = 0;
        itemsCount = 0;
        productsCount = 0;
        for (Map.Entry<Product,Integer> record : products.entrySet()){
            total += record.getKey().getListPrice() * record.getValue();
            itemsCount += record.getValue();
            productsCount++;
        }
    }

    public Integer getTotal() {
        return total;
    }

    public Integer getItemsCount() {
        return itemsCount;
    }

    public Integer getProductsCount() {
        return productsCount;
    }

    public Map<Product,Integer> getProducts(){
        return products;
    }
}
