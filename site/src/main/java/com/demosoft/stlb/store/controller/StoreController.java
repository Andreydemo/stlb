package com.demosoft.stlb.store.controller;

import com.demosoft.stlb.store.ProductService;
import com.demosoft.stlb.store.entity.Product;
import com.demosoft.stlb.store.entity.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by andrii_korkoshko on 10.05.16.
 */
@Controller
@RequestMapping("/shop")
public class StoreController {


    @Autowired
    private ProductService productService;

    @Autowired
    private ShoppingCart shoppingCart;

    @RequestMapping(method = RequestMethod.GET)
    public String index(){
        return "shopIndex";
    }

    @RequestMapping(value = "/catalog", method = RequestMethod.GET)
    public String catalog(Model model){
        model.addAttribute("products",productService.getAllProducts());
        model.addAttribute("cart",shoppingCart);
        return "catalog";
    }

    @RequestMapping(value = "/catalog", method = RequestMethod.POST)
    public String addToCart (@RequestParam("productId") String productId , @RequestParam("quantity") String quantity,
                             Model model) {
        Integer parsedQuantity = null;
        if(quantity != null && !quantity.isEmpty()){
            try {
                parsedQuantity = Integer.valueOf(quantity);
            } catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
        Product product = productService.getProductById(productId);
        if(product != null) {
            if (parsedQuantity != null) {
                shoppingCart.addProduct(product,parsedQuantity);
            } else {
                shoppingCart.addProduct(product);
            }
        }

        return "redirect:catalog";
    }
    @RequestMapping(value = "/checkout", method = RequestMethod.GET)
    public String checkout(Model model){
        model.addAttribute("cart",shoppingCart);
        return "checkout";
    }
}
