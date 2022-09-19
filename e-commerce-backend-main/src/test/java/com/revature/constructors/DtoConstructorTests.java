package com.revature.constructors;

import com.revature.dtos.AddressRequest;
import com.revature.dtos.LoginRequest;
import com.revature.dtos.PaymentRequest;
import com.revature.dtos.RegisterRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DtoConstructorTests {
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
    String ccNumber = "12345";
    String expPeriod = "032020";
    String svcCode = "123";

    @Test
    void testAddressRequestAllArgs() {
        AddressRequest testAddressRequest = new AddressRequest(this.city, this.country, this.state, this.street,
                this.zipCode);

        assertEquals(this.city, testAddressRequest.getCity());
        assertEquals(this.country, testAddressRequest.getCountry());
        assertEquals(this.state, testAddressRequest.getState());
        assertEquals(this.street, testAddressRequest.getStreet());
        assertEquals(this.zipCode, testAddressRequest.getZipCode());
    }

    @Test
    void testAddressRequestNoArgs() {
        AddressRequest testAddressRequest = new AddressRequest();

        assertNull(testAddressRequest.getCity());
        assertNull(testAddressRequest.getCountry());
        assertNull(testAddressRequest.getState());
        assertNull(testAddressRequest.getStreet());
        assertNull(testAddressRequest.getZipCode());
    }

    @Test
    void testLoginRequestAllArgs() {
        LoginRequest testLoginRequest = new LoginRequest(this.email, this.password);

        assertEquals(this.email, testLoginRequest.getUserEmail());
        assertEquals(this.password, testLoginRequest.getUserPassword());
    }

    @Test
    void testLoginRequestNoArgs() {
        LoginRequest testLoginRequest = new LoginRequest();

        assertNull(testLoginRequest.getUserEmail());
        assertNull(testLoginRequest.getUserPassword());
    }

    @Test
    void testPaymentRequestAllArgs() {
        PaymentRequest testPaymentRequest = new PaymentRequest(this.ccNumber, this.expPeriod, this.svcCode,
                this.zipCode);

        assertEquals(this.ccNumber, testPaymentRequest.getCcNumber());
        assertEquals(this.expPeriod, testPaymentRequest.getExpPeriod());
        assertEquals(this.svcCode, testPaymentRequest.getSvcCode());
        assertEquals(this.zipCode, testPaymentRequest.getZipCode());
    }

    @Test
    void testPaymentRequestNoArgs() {
        PaymentRequest testPaymentRequest = new PaymentRequest();

        assertNull(testPaymentRequest.getCcNumber());
        assertNull(testPaymentRequest.getExpPeriod());
        assertNull(testPaymentRequest.getSvcCode());
        assertNull(testPaymentRequest.getZipCode());
    }

    @Test
    void testRegisterRequestAllArgs() {
        RegisterRequest testRegisterRequest = new RegisterRequest(this.fName, this.lName, this.email, this.password);

        assertEquals(this.fName, testRegisterRequest.getFirstName());
        assertEquals(this.lName, testRegisterRequest.getLastName());
        assertEquals(this.email, testRegisterRequest.getUserEmail());
        assertEquals(this.password, testRegisterRequest.getUserPassword());
    }

    @Test
    void testRegisterRequestNoArgs() {
        RegisterRequest testRegisterRequest = new RegisterRequest();

        assertNull(testRegisterRequest.getFirstName());
        assertNull(testRegisterRequest.getLastName());
        assertNull(testRegisterRequest.getUserEmail());
        assertNull(testRegisterRequest.getUserPassword());
    }
}
