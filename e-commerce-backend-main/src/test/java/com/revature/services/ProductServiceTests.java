package com.revature.services;

import com.revature.exceptions.ProductNotFoundException;
import com.revature.models.Product;
import com.revature.repositories.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ProductServiceTests {

    @Mock
    ProductRepository mockProdRepo;
    @InjectMocks
    ProductService pServ;

    List<Product> dummyList = new ArrayList<>();

    Product dummyProd;
    // Product dummyProd2;

    @BeforeEach
    void setUp() throws Exception {
        this.dummyProd = new Product(1, 1, "someDesc", "someImage", "Somename");
        // this.dummyProd2 = new Product(2, 1, 1, "someDesc2", "someImage2",
        // "someName2");
        this.dummyList.add(0, this.dummyProd);
        // this.dummyList.add(1, this.dummyProd2);
    }

    @AfterEach
    void tearDown() throws Exception {
        this.dummyProd = null;
        this.dummyList.remove(0);
    }

    @Test
    void findById() {
        int id = 1;

        given(this.mockProdRepo.findById(id)).willReturn(Optional.of(this.dummyProd));

        Optional<Product> expected = Optional.of(this.dummyProd);
        Optional<Product> actual = this.pServ.findById(id);

        assertEquals(expected, actual);

    }

    @Test
    void testFindById_Failure_ProductNotFoundException() {

        int id = 2;

        // when the service layer calls on the repo layer, it returns an empty Optional
        given(this.mockProdRepo.findById(id)).willReturn(Optional.empty());

        try {
            this.pServ.findById(id);
        } catch (Exception e) {
            // prove that the Exception thrown was indeed a ProductNotFoundException
            assertEquals(ProductNotFoundException.class, e.getClass());
        }

    }

    // @Test
    // void findAll() {
    // }

    @Test
    void findByName() {
        String name = dummyProd.getProdName();
        given(this.mockProdRepo.findByprodName(name)).willReturn(this.dummyList);

        Optional<List<Product>> expected = Optional.of(this.dummyList);
        Optional<List<Product>> actual = this.pServ.findByName(dummyProd.getProdName());

        assertEquals(expected, actual);

    }

    @Test
    void findByName_Failure_ProductNotFoundException() {
        String name = "Notaproduct";

        // when the service layer calls on the repo layer, it returns an empty Optional
        given(this.mockProdRepo.findByprodName(name)).willReturn(Collections.emptyList());

        try {
            this.pServ.findByName(name);
        } catch (Exception e) {
            // prove that the Exception thrown was indeed a ProductNotFoundException
            assertEquals(ProductNotFoundException.class, e.getClass());
        }

    }

    @Test
    void findByName_StringCheck01() {
        //Note: these functions could be shifted to a "helper" method in the service layer, which would make testing more logical
        String prodName = "testName";

        String prodNameMod01 = prodName.substring(0,1).toUpperCase() + prodName.substring(1).toLowerCase();

        String expected = "Testname";
        String actual = prodNameMod01;

        assertEquals(expected, actual);

    }

    @Test
    void findByName_StringCheck02() {
        String prodName = "testName";

        String prodNameMod02 = prodName.substring(0,1).toUpperCase() + prodName.substring(1);

        String expected = "TestName";
        String actual = prodNameMod02;

        assertEquals(expected, actual);

    }

    @Test
    void findByDescription() {
        String desc = "someDesc";
        given(this.mockProdRepo.findByprodDesc(desc)).willReturn(Optional.of(this.dummyList));

        Optional<List<Product>> expected = Optional.of(this.dummyList);
        Optional<List<Product>> actual = this.pServ.findByDescription(desc);

        assertEquals(expected, actual);
    }

    @Test
    void findByDescription_Failure_ProductNotFoundException() {
        String desc = "anotherDesc";

        // when the service layer calls on the repo layer, it returns an empty Optional
        given(this.mockProdRepo.findByprodDesc(desc)).willReturn(Optional.empty());

        try {
            this.pServ.findByDescription(desc);
        } catch (Exception e) {
            // prove that the Exception thrown was indeed a ProductNotFoundException
            assertEquals(ProductNotFoundException.class, e.getClass());
        }

    }

    @Test
    void findByImage() {
        String image = "someImage";
        given(this.mockProdRepo.findByprodImage(image)).willReturn(Optional.of(this.dummyList));

        Optional<List<Product>> expected = Optional.of(this.dummyList);
        Optional<List<Product>> actual = this.pServ.findByImage(image);

        assertEquals(expected, actual);
    }

    @Test
    void findByImage_Failure_ProductNotFoundException() {

        String image = "anotherImage";

        // when the service layer calls on the repo layer, it returns an empty Optional
        given(this.mockProdRepo.findByprodImage(image)).willReturn(Optional.empty());

        try {
            this.pServ.findByImage(image);
        } catch (Exception e) {
            // prove that the Exception thrown was indeed a ProductNotFoundException
            assertEquals(ProductNotFoundException.class, e.getClass());
        }

    }

    @Test
    void findByPrice() {
        double price = 1;
        given(this.mockProdRepo.findByprodPrice(price)).willReturn(Optional.of(this.dummyList));

        Optional<List<Product>> expected = Optional.of(this.dummyList);
        Optional<List<Product>> actual = this.pServ.findByPrice(price);

        assertEquals(expected, actual);
    }

    @Test
    void findByPrice_Failure_ProductNotFoundException() {
        double price = 2.05;

        // when the service layer calls on the repo layer, it returns an empty Optional
        given(this.mockProdRepo.findByprodPrice(price)).willReturn(Optional.empty());

        try {
            this.pServ.findByPrice(price);
        } catch (Exception e) {
            // prove that the Exception thrown was indeed a ProductNotFoundException
            assertEquals(ProductNotFoundException.class, e.getClass());
        }

    }
}