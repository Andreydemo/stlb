package com.demosoft.stlb.store.entity;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by andrii_korkoshko on 10.05.16.
 */
public class Product {

    private String id;
    private String name;
    private Integer listPrice;
    private Integer salesPrice;
    private String description;
    private List<String> images;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getListPrice() {
        return listPrice;
    }

    public void setListPrice(Integer listPrice) {
        this.listPrice = listPrice;
    }

    public Integer getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(Integer salesPrice) {
        this.salesPrice = salesPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (id != null ? !id.equals(product.id) : product.id != null) return false;
        if (name != null ? !name.equals(product.name) : product.name != null) return false;
        if (listPrice != null ? !listPrice.equals(product.listPrice) : product.listPrice != null) return false;
        if (salesPrice != null ? !salesPrice.equals(product.salesPrice) : product.salesPrice != null) return false;
        if (description != null ? !description.equals(product.description) : product.description != null) return false;
        return images != null ? images.equals(product.images) : product.images == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (listPrice != null ? listPrice.hashCode() : 0);
        result = 31 * result + (salesPrice != null ? salesPrice.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (images != null ? images.hashCode() : 0);
        return result;
    }
}
