package com.revature.controllers;

import com.revature.exceptions.ProductNotFoundException;
import com.revature.exceptions.WishNotFoundException;
import com.revature.models.Product;
import com.revature.models.User;
import com.revature.models.Wish;
import com.revature.services.ProductService;
import com.revature.services.UserService;
import com.revature.services.WishService;
import com.revature.util.JwtTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:3000",
        "http://localhost:5000", "http://e-commerce-shrooster.s3-website-us-east-1.amazonaws.com" }, allowedHeaders = "*")
@RequestMapping("/wish")
public class WishController {

    private WishService wishService;
    private JwtTokenManager tokenManager;
    private UserService userService;
    private ProductService productService;

    @Autowired
    public WishController(WishService wishService, JwtTokenManager tokenManager, UserService userService,
            ProductService productService) {
        this.wishService = wishService;
        this.tokenManager = tokenManager;
        this.userService = userService;
        this.productService = productService;
    }

    // This is the end point used to get all of the items in a user's wish list by user ID.
    @GetMapping("/get/{user_id}")
    public ResponseEntity<List<Product>> getWishesByUserId(@PathVariable("user_id") int userId) {
        // We take in a user ID passed in through the endpoint path and use it to call our wish service, passing it
        // the user ID and saving the result into a List of Wishes.
        List<Wish> wishes = new ArrayList<Wish>(wishService.getWishesByUserId(userId));
        List<Product> prodList = new ArrayList<Product>();
        // From the list of wishes we create a list of all the products on the wish list and return that list
        // to the front end.
        for (int i = 0; i <= wishes.size() - 1; i++) {
            Product item = wishes.get(i).getProduct();
            prodList.add(item);
        }
        return ResponseEntity.ok(prodList);
    }

    // This is the end point for adding a product to the wish list using a user ID and Product ID
    @PostMapping("/post/{user_id}/{prod_id}")
    public ResponseEntity<Wish> addWish(@PathVariable("user_id") int userId,
            @PathVariable("prod_id") int prodId) {
        // try {
        // wishService.getWishByUserIdAndProductId(userId, prodId);
        // } catch(WishNotFoundException e) {
        // return new ResponseEntity<Wish>(HttpStatus.NOT_FOUND);
        // }

        // In order to create a wish list item we first need to make full objects of the user and product
        // by passing in the IDs into the user and product services.
        User user = userService.findByUserId(userId);
        System.out.println(user + "Full user");
        Product product = productService.findById(prodId)
                .orElseThrow(() -> new ProductNotFoundException("No product with id " + prodId + " found."));
        System.out.println(product + "Full product");
        // We then pass these full objects into the wish service level.
        return ResponseEntity.ok(wishService.addWish(user, product));
    }

    // This is the end point for deleting a product from the wish list using a user ID and Product ID
    @DeleteMapping("/delete/{user_id}/{prod_id}")
    public void deleteWish(@PathVariable("user_id") int userId, @PathVariable("prod_id") int prodId) {
        // First we create full object using the IDs passed in through the endpoint.
        User user = userService.findByUserId(userId);
        Product product = productService.findById(prodId)
                .orElseThrow(() -> new ProductNotFoundException("No product with id " + prodId + " found."));
        // We then pass these full objects into the wish service level.
        wishService.deleteWish(user, product);
    }

    // This is the end point for deleting all products from the wish list for a specific user by using
    // the user's ID.
    @DeleteMapping("/delete_all/{user_id}")
    public void deleteAllWishes(@PathVariable("user_id") int userId) {
        User user = userService.findByUserId(userId);
        // After making a full user object we pass that object into the service layer.
        wishService.deleteAllWishes(user);
    }

}
