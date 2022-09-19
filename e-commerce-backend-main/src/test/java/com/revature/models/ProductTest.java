package com.revature.models;

import com.revature.exceptions.ProductNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ProductTest {

    Product dummyProd;

    @BeforeEach
    void setUp() throws Exception {
        this.dummyProd = new Product(1, 1, "someDesc", "someImage", "someName");
    }

    @AfterEach
    void tearDown() throws Exception {
        this.dummyProd = null;
    }

    @Test
    void getProdId() {

        int expected = 0;
        int actual = this.dummyProd.getProdId();

        assertEquals(expected, actual);
    }

    @Test
    void getProdQuantity() {
        int expected = 1;
        int actual = this.dummyProd.getProdQuantity();

        assertEquals(expected, actual);
    }

    @Test
    void getProdPrice() {
        double expected = 1;
        double actual = this.dummyProd.getProdPrice();

        assertEquals(expected, actual);
    }

    @Test
    void getProdDesc() {
        String expected = "someDesc";
        String actual = this.dummyProd.getProdDesc();

        assertEquals(expected, actual);
    }

    @Test
    void getProdImage() {
        String expected = "someImage";
        String actual = this.dummyProd.getProdImage();

        assertEquals(expected, actual);
    }

    @Test
    void getProdName() {
        String expected = "someName";
        String actual = this.dummyProd.getProdName();

        assertEquals(expected, actual);
    }

//    @Test
//    void getQuantityBoughts() {
//        List<OrderQuantityBought> expected = null;
//        List<OrderQuantityBought> actual = this.dummyProd.getQuantityBoughts();
//
//        assertEquals(expected, actual);
//    }

    @Test
    void setProdId() {
        this.dummyProd.setProdId(1);
        int expected = 1;
        int actual = this.dummyProd.getProdId();
        assertEquals(expected, actual);
    }

    @Test
    void setProdQuantity() {
        this.dummyProd.setProdQuantity(2);
        int expected = 2;
        int actual = this.dummyProd.getProdQuantity();
        assertEquals(expected, actual);
    }

    @Test
    void setProdPrice() {
        this.dummyProd.setProdPrice(2);
        double expected = 2;
        double actual = this.dummyProd.getProdPrice();
        assertEquals(expected, actual);
    }

    @Test
    void setProdDesc() {
        this.dummyProd.setProdDesc("blank");
        String expected = "blank";
        String actual = this.dummyProd.getProdDesc();
        assertEquals(expected, actual);
    }

    @Test
    void setProdImage() {
        this.dummyProd.setProdImage("no image");
        String expected = "no image";
        String actual = this.dummyProd.getProdImage();
        assertEquals(expected, actual);
    }

    @Test
    void setProdName() {
        this.dummyProd.setProdName("no name");
        String expected = "no name";
        String actual = this.dummyProd.getProdName();
        assertEquals(expected, actual);
    }

//    @Test
//    void setQuantityBoughts() {
//    }
}