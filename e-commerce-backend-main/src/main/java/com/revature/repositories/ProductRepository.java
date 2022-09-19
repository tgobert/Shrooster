package com.revature.repositories;

import com.revature.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<List<Product>> findByprodDesc(String prodDesc);
    //Uses pattern matching to find products based on partial character matches in prodDesc field

    @Query("FROM Product WHERE prodName LIKE %:prodName%")
    List<Product> findByprodName(@Param(value = "prodName") String prodName);
    //Uses pattern matching to find products based on partial character matches in prodName field

    @Query("FROM Product WHERE prodImage LIKE %:prodImage%")
    Optional<List<Product>> findByprodImage(@Param(value = "prodImage") String prodImage);
    //Uses pattern matching to find products based on partial character matches in prodImage field
    //Note -- prodImage refers to an image URL

    Optional<List<Product>> findByprodPrice(double prodPrice);
    //Checks for exact price

    @Query("FROM Product WHERE prodPrice BETWEEN :priceMin AND :priceMax")
    Optional<List<Product>> findByprodPrice(@Param(value = "priceMin") double priceMin, @Param(value = "priceMax") double priceMax);
    //Checks for products within price ranged defined by priceQueryRangeMin/Max
}
