package com.demosoft.stlb.store.entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by andrii_korkoshko on 10.05.16.
 */
public class Order {

    private Map<String,Integer> products = new HashMap<>();
    private Set<Product> productSet = new HashSet<>();
    private Map<String,Product> productMap= new HashMap<>();
    private Integer total = 0;
    private Integer itemsCount = 0;
    private Integer productsCount = 0;

    public void addProduct(Product product){
        addProduct(product,1);
        reprice();
    }

    public void addProduct (Product product, Integer newQuantity){
        Integer quantity = products.get(product.getId());
        productMap.put(product.getId(),product);
        productSet.add(product);
        if(quantity == null || quantity.equals(0)){
            products.put(product.getId(),new Integer(newQuantity));
        } else {
            quantity += newQuantity;
            products.put(product.getId(),new Integer(quantity));
        }
        reprice();
    }

    public void reprice(){
        total = 0;
        itemsCount = 0;
        productsCount = 0;
        for (Map.Entry<String,Integer> record : products.entrySet()){
            total += productMap.get(record.getKey()).getListPrice() * record.getValue();
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

    public Map<String,Integer> getProducts(){
        return products;
    }

    public void setProducts(Map<String, Integer> products) {
        this.products = products;
    }

    public Set<Product> getProductSet() {
        return productSet;
    }

    public void setProductSet(Set<Product> productSet) {
        this.productSet = productSet;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public void setItemsCount(Integer itemsCount) {
        this.itemsCount = itemsCount;
    }

    public void setProductsCount(Integer productsCount) {
        this.productsCount = productsCount;
    }


    public Map<String, Product> getProductMap() {
        return productMap;
    }

    public void setProductMap(Map<String, Product> productMap) {
        this.productMap = productMap;
    }
}
