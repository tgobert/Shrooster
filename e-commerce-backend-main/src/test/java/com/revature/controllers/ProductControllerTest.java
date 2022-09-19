package com.revature.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.revature.dtos.ProductDTO;
import com.revature.exceptions.ProductNotFoundException;
import com.revature.models.Product;
import com.revature.repositories.ProductRepository;
import com.revature.services.ProductService;
import com.revature.controllers.ProductController;
import org.apache.coyote.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    ProductRepository testProdRep;
    @InjectMocks
    ProductService testProdSer;
    @InjectMocks
    ProductController testProdCon;

    Product testProduct1;
    Product testProduct2;
    ProductDTO testProdTO;
    List<Product> testProdList;
    List<ProductDTO> testTOList;

    // Test Setup
    @BeforeEach
    void setUp() {
        this.testProdSer = new ProductService(testProdRep);
        this.testProdCon = new ProductController(testProdSer);

        this.testProduct1 = new Product(20, 20.5, "someProdDesc", "someProdImage", "someProdName");
        this.testProduct2 = new Product(15, 15.5, "someOtherDesc", "someOtherImage", "someOtherName");

        this.testProdList = new ArrayList<>();
        this.testProdList.add(testProduct1);
        this.testProdTO = new ProductDTO(1, 1);
        this.testTOList = new ArrayList<>();
        this.testTOList.add(this.testProdTO);
    }

    @AfterEach
    void tearDown() {
        this.testProduct1 = null;
        this.testProduct2 = null;
        this.testProdTO = null;
        this.testProdList = null;
        this.testTOList = null;
        this.testProdCon = null;
        this.testProdSer = null;
    }

    // Test Collection
    @Test
    void getInventory() {
    }

    @Test
    void getProdById_SUCCESS() {

        given(this.testProdRep.findById(testProduct1.getProdId())).willReturn(Optional.of(this.testProduct1));
        given(this.testProdSer.findById(testProduct1.getProdId())).willReturn(Optional.of(this.testProduct1));

        Product expected = this.testProduct1;
        Product actual = (this.testProdCon.getProductById(testProduct1.getProdId())).getBody();

        assertEquals(expected, actual);
    }

    @Test
    void getProdById_FAILURE_ThrowsException() {

        int invalidID = -1;

        given(this.testProdSer.findById(invalidID)).willReturn(Optional.empty());

        try {
            this.testProdCon.getProductById(invalidID);
        } catch (Exception e) {
            // prove that the Exception thrown was indeed a ProductNotFoundException
            assertEquals(ProductNotFoundException.class, e.getClass());
        }
    }

    @Test
    void upsertProduct() {
    }

    @Test
    void purchaseProduct_SUCCESS() {
    }

    @Test
    void purchaseProduct_FAILURE_InvException() {
    }

    @Test
    void deleteProduct() {

        int prodID = this.testProduct1.getProdId();

        given(this.testProdSer.findById(prodID)).willReturn(Optional.of(this.testProduct1));
        ResponseEntity<Product> actual = this.testProdCon.deleteProduct(prodID);
        ResponseEntity<Product> expected = ResponseEntity.of(Optional.of(this.testProduct1));

        assertEquals(actual, expected);
    }

    //A note on polySearch()
    //The function takes in an appended httpQuery from the request utilizing @RequestParam
    //rivanman was unsure how exactly to pass that as an argument in the function, however everything else is outlined below for testing
    @Test
    void polyProductSearch_nameQuery() {
//        String nameQuery = this.testProduct1.getProdName();
//
//        given(this.testProdSer.findByName(testProduct1.getProdName())).willReturn(Optional.of(this.testProdList));
//
//        Optional<List<Product>> expected = Optional.of(this.testProdList);
//        Optional<List<Product>> actual = this.testProdCon.polyProductSearch(nameQuery);
//
//        assertEquals(expected, actual);
    }

    @Test
    void polyProductSearch_descQuery() {

//        String descQuery = this.testProduct1.getProdDesc();
//
//        given(this.testProdSer.findByDescription(testProduct1.getProdDesc())).willReturn(Optional.of(this.testProdList));
//
//        Optional<List<Product>> expected = Optional.of(this.testProdList);
//        Optional<List<Product>> actual = this.testProdCon.polyProductSearch(descQuery);
//
//        assertEquals(expected, actual);
    }

    @Test
    void polyProductSearch_imageQuery() {

//        String imageQuery = this.testProduct1.getProdImage();
//
//        given(this.testProdSer.findByImage(testProduct1.getProdImage())).willReturn(Optional.of(this.testProdList));
//
//        Optional<List<Product>> expected = Optional.of(this.testProdList);
//        Optional<List<Product>> actual = this.testProdCon.polyProductSearch(imageQuery);
//
//        assertEquals(expected, actual);
    }

    @Test
    void polyProductSearch_priceQuery() {

//        double priceQuery = this.testProduct1.getProdPrice();
//
//        given(this.testProdSer.findByPrice(testProduct1.getProdPrice())).willReturn(Optional.of(this.testProdList));
//
//        Optional<List<Product>> expected = Optional.of(this.testProdList);
//        Optional<List<Product>> actual = this.testProdCon.polyProductSearch(priceQuery);
//
//        assertEquals(expected, actual);
    }

    @Test
    void polyProductSearch_priceRangeQuery() {

//        double priceQueryRangeMin = 0;
//        double priceQueryRangeMax = 100;
//
//        given(this.testProdSer.findByPriceRange(priceQueryRangeMin, priceQueryRangeMax)).willReturn(Optional.of(this.testProdList));
//
//        Optional<List<Product>> expected = Optional.of(this.testProdList);
//        Optional<List<Product>> actual = this.testProdCon.polyProductSearch(priceQueryRangeMin, priceQueryRangeMax);
//
//        assertEquals(expected, actual);
    }
}