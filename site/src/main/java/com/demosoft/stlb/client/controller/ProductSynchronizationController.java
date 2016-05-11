package com.demosoft.stlb.client.controller;

import com.demosoft.stlb.store.entity.Product;
import com.demosoft.stlb.store.storage.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by andrii_korkoshko on 11.05.16.
 */
@Controller
@RequestMapping(value = "/product")
public class ProductSynchronizationController {

    @Autowired
    private Repository repository;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public @ResponseBody List<Product> getAllProducts(){
        return  repository.getAllProducts();
    }

}
