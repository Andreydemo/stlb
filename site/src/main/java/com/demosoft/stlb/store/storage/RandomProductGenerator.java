package com.demosoft.stlb.store.storage;

import com.demosoft.stlb.store.entity.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by andrii_korkoshko on 10.05.16.
 */
@Component
public class RandomProductGenerator {


    public static int minPrice = 100;
    public static int priceDifference = 400;
    public static int priceSaleDifference = 50;



    public static String watch="Watch";


    public static String[] productPrefix = new String[] {
            "Grate",
            "Men's",
            "King's",
            "Cheap"};

    public static String[] productAttribute = new String[] {
            "Gold",
            "Blue",
            "Red",
            "Black",
            "Yellow,",
            "Platinum",
            "Exclusive"};

    public static String[] images = new String[] {
            "m1.jpg",
            "m2.jpg",
            "m3.jpg",
            "m4.jpg",
            "m5.jpg",
            "m6.jpg"
            };


    protected String generateName(){
        StringBuilder nameBuilder = new StringBuilder();
        Random random = new Random();
        nameBuilder.append(productPrefix[ random.nextInt(productPrefix.length)]).append(" ");
        nameBuilder.append(productAttribute[ random.nextInt(productAttribute.length)]).append(" ");
        nameBuilder.append(watch);
        return nameBuilder.toString();
    }


    protected int[] generatePrice(){
        int [] prices = new int[2];
        prices[0] = minPrice +  new Random().nextInt(priceDifference);
        prices[1] = prices[0] - new Random().nextInt(priceSaleDifference);

        return prices;
    }

    protected List<String> generateImages(){
        List<String> images = new ArrayList<>(2);
        Random random = new Random();
        images.add(this.images[ random.nextInt(this.images.length)]);
        images.add(this.images[ random.nextInt(this.images.length)]);
        return images;
    }


    public Product generate(){
        Product product = new Product();
        product.setId(UUID.randomUUID().toString());
        product.setName(generateName());
        product.setDescription(product.getName());
        int[] prices = generatePrice();
        product.setListPrice(prices[0]);
        product.setSalesPrice(prices[1]);
        product.setImages(generateImages());
        return  product;
    }
    public List<Product> generateList(int quantity){
        List<Product> products = new ArrayList<>(quantity);
        for (int i = 0 ; i < quantity ; i++ ) {
            products.add(generate());
        }
        return  products;
    }
}
