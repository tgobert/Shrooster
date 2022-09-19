package com.revature.services;

import com.revature.models.*;
import com.revature.repositories.WishRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WishServiceTest {

    @Mock
    private WishRepository wishRepoTest;

    @InjectMocks
    private WishService wishServiceTest;

    User dummyUser;
    Wish dummyWish1;
    Wish dummyWish2;
    WishKey dummyWishKey1;
    WishKey dummyWishKey2;
    Product dummyProduct1;
    Product dummyProduct2;
    List<Wish> dummyWishList = new ArrayList<>();

    @BeforeEach
    void setupUser() throws Exception {
        this.dummyUser = new User(1, "dummy@mail.com", "P@$$w0rd", "Dummy", "User", "token");
        this.dummyProduct1 = new Product(1, "Chicken Hat");
        this.dummyProduct2 = new Product(2, "Chicken Pants");
        this.dummyWishKey1 = new WishKey(1, 1);
        this.dummyWishKey2 = new WishKey(1, 2);
        this.dummyWish1 = new Wish(dummyWishKey1, dummyUser, dummyProduct1);
        this.dummyWish2 = new Wish(dummyWishKey2, dummyUser, dummyProduct2);
        this.dummyWishList.add(dummyWish1);
        this.dummyWishList.add(dummyWish2);
    }

    @AfterEach
    void tearDown() throws Exception {
        this.dummyUser = null;
        this.dummyProduct1 = null;
        this.dummyProduct2 = null;
        this.dummyWishKey1 = null;
        this.dummyWishKey2 = null;
        this.dummyWish1 = null;
        this.dummyWish2 = null;
        this.dummyWishList = null;
    }

    @Test
    void testGetAllWishesById_success() {
        int userId = 1;
        given(this.wishRepoTest.findByUserId(userId)).willReturn(Optional.of(this.dummyWishList));
        List<Wish> expected = this.dummyWishList;
        List<Wish> actual = wishServiceTest.getWishesByUserId(dummyUser.getUserId());
        assertEquals(expected, actual);
    }

    @Test
    void testGetAllWishesById_failure() {
        int userId = 2;
        List<Wish> expected = null;
        List<Wish> actual = this.wishServiceTest.getWishesByUserId(userId);
        assertEquals(expected, actual);
    }

    @Test
    void testAddWish_success() {
        given(this.wishRepoTest.save(this.dummyWish1)).willReturn(this.dummyWish1);
        Wish expected = this.dummyWish1;
        Wish actual = wishServiceTest.addWish(dummyUser, dummyProduct1);
        assertEquals(expected, actual);
    }

    @Test
    void testAddWish_noUserFailure() {
        User user10 = new User(10, "dummy2@mail.com", "P@$$w0rd", "Dummy", "User", "token");
        assertNull(wishServiceTest.addWish(user10, dummyProduct1));
    }

    @Test
    void testAddWish_noProductFailure() {
        Product prod10 = new Product(10, "Chicken");
        assertNull(wishServiceTest.addWish(dummyUser, prod10));
    }
}
