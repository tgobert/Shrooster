package com.revature.services;

import com.revature.dtos.ProductDTO;
import com.revature.models.Product;
import com.revature.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(int id) {
        return productRepository.findById(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public List<Product> saveAll(List<Product> productList) {
        return productRepository.saveAll(productList);
    }

    public void delete(int id) {
        productRepository.deleteById(id);
    }

    // Special requests, new stuff
    // For loose-matched search function

    public Optional<List<Product>> findByName(String prodName) {
        //SQL queries are case-sensitive, rather than calling the entire repository into this space and running a case-insensitive query...
        //Takes string prodName and performs a few basic modifications, then if(prodList) is not populated, runs additional search queries to the repository
        String prodNameMod01 = prodName.substring(0,1).toUpperCase() + prodName.substring(1).toLowerCase();
        String prodNameMod02 = prodName.substring(0,1).toUpperCase() + prodName.substring(1);
        List<Product> prodList = productRepository.findByprodName(prodName);
        if(prodList.isEmpty()) {
            //prodNameMod01 capitalizes first letter and de-capitalizes the rest of the string
            prodList = productRepository.findByprodName(prodNameMod01);
            if(prodList.isEmpty()) {
                //prodNameMod02 only capitalizes the first letter
                prodList = productRepository.findByprodName(prodNameMod02);
            }
        }
        return Optional.of(prodList);
    }

    // Calls the user-defined findByprodDesc method defined in the product
    // repository layer
    public Optional<List<Product>> findByDescription(String descQuery) {
        return productRepository.findByprodDesc(descQuery);
    }

    // Calls the user-defined findByprodImage method defined in the product
    // repository layer
    public Optional<List<Product>> findByImage(String imageQuery) {
        return productRepository.findByprodImage(imageQuery);
    }

    // Calls the user-defined findByprodPrice method defined in the product
    // repository layer
    public Optional<List<Product>> findByPrice(double priceQuery) {
        return productRepository.findByprodPrice(priceQuery);
    }

    public Optional<List<Product>> findByPriceRange(double priceMin, double priceMax) {
        return productRepository.findByprodPrice(priceMin, priceMax);
    }
}