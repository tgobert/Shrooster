package com.revature.constructors;

import com.revature.models.Address;
import com.revature.models.Image;
import com.revature.models.Payment;
import com.revature.models.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ModelsConstructorTests {
    int idUser = 1;
    int idAddress = 1;
    int idPicture = 1;
    Integer idPayment = 1;
    String email = "test@gmail.com";
    String password = "password12345";
    String fName = "Sam";
    String lName = "Freedom";
    String token = "123456789";
    String street = "5th Avenue";
    String city = "New York";
    String state = "NY";
    String country = "United States";
    String zipCode = "12345";
    String picName = "dog";
    String picType = "png";
    String ccNumber = "12345";
    String expPeriod = "032020";
    String svcCode = "123";
    byte[] picByte = {1, 2};

    @Test
    void testUserAllArgs() {
        User testUser = new User(this.idUser, this.email, this.password, this.fName, this.lName, this.token,
                null, null, null);

        assertEquals(this.idUser, testUser.getUserId());
        assertEquals(this.email, testUser.getUserEmail());
        assertEquals(this.password, testUser.getUserPassword());
        assertEquals(this.fName, testUser.getFirstName());
        assertEquals(this.lName, testUser.getLastName());
        assertEquals(this.token, testUser.getTokenId());
        assertNull(testUser.getImages());
        assertNull(testUser.getAddresses());
        assertNull(testUser.getPayments());
    }

    @Test
    void testUserNoArgs() {
        User testUser = new User();

        assertEquals(0, testUser.getUserId());
        assertNull(testUser.getUserEmail());
        assertNull(testUser.getUserPassword());
        assertNull(testUser.getFirstName());
        assertNull(testUser.getLastName());
        assertNull(testUser.getTokenId());
        assertEquals(new ArrayList<Image>(), testUser.getImages());
        assertEquals(new ArrayList<Address>(), testUser.getAddresses());
        assertEquals(new ArrayList<Payment>(), testUser.getPayments());
    }

    @Test
    void testAddressAllArgs() {
        Address testAddress = new Address(this.idAddress, this.street, this.city, this.state, this.country,
                this.zipCode, null);

        assertEquals(this.idAddress, testAddress.getAddressId());
        assertEquals(this.street, testAddress.getStreet());
        assertEquals(this.city, testAddress.getCity());
        assertEquals(this.state, testAddress.getState());
        assertEquals(this.country, testAddress.getCountry());
        assertEquals(this.zipCode, testAddress.getZipCode());
        assertNull(testAddress.getUser());
    }

    @Test
    void testAddressNoArgs() {
        Address testAddress = new Address();

        assertNull(testAddress.getAddressId());
        assertNull(testAddress.getStreet());
        assertNull(testAddress.getCity());
        assertNull(testAddress.getState());
        assertNull(testAddress.getCountry());
        assertNull(testAddress.getZipCode());
        assertNull(testAddress.getUser());
    }

    @Test
    void testImageAllArgs() {
        Image testImage = new Image(this.idPicture, this.picName, this.picType, this.picByte,  null);

        assertEquals(this.idPicture, testImage.getPicId());
        assertEquals(this.picName, testImage.getPicName());
        assertEquals(this.picType, testImage.getPicType());
        assertEquals(this.picByte, testImage.getPicByte());
        assertNull(testImage.getUser());
    }

    @Test
    void testImageNoArgs() {
        Image testImage = new Image();

        assertEquals(0, testImage.getPicId());
        assertNull(testImage.getPicName());
        assertNull(testImage.getPicType());
        assertNull(testImage.getPicByte());
        assertNull(testImage.getUser());
    }

    @Test
    void testPaymentAllArgs() {
        Payment testPayment = new Payment(this.idPayment, this.ccNumber, this.expPeriod, this.zipCode,
                this.svcCode, null);

        assertEquals(this.idPayment, testPayment.getPaymentId());
        assertEquals(this.ccNumber, testPayment.getCcNumber());
        assertEquals(this.expPeriod, testPayment.getExpPeriod());
        assertEquals(this.zipCode, testPayment.getZipCode());
        assertEquals(this.svcCode, testPayment.getSvcCode());
        assertNull(testPayment.getUser());
    }

    @Test
    void testPaymentNoArgs() {
        Payment testPayment = new Payment();

        assertEquals(null, testPayment.getPaymentId());
        assertNull(testPayment.getCcNumber());
        assertNull(testPayment.getExpPeriod());
        assertNull(testPayment.getZipCode());
        assertNull(testPayment.getSvcCode());
        assertNull(testPayment.getUser());

    }

}
