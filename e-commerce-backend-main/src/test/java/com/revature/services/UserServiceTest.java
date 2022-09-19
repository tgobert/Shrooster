package com.revature.services;

import com.revature.exceptions.AuthenticationException;
import com.revature.models.Address;
import com.revature.models.Image;
import com.revature.models.Payment;
import com.revature.models.User;
import com.revature.repositories.AddressRepository;
import com.revature.repositories.ImageRepository;
import com.revature.repositories.PaymentRepository;
import com.revature.repositories.UserRepository;
import com.revature.util.JwtTokenManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
//   CLASS ATTRIBUTES
    @Mock
    UserRepository mockUserRepository;
    @Mock
    AddressRepository mockAddressRepository;
    @Mock
    PaymentRepository mockPaymentRepository;
    @Mock
    ImageRepository mockImageRepository;
    @Mock
    JwtTokenManager mockTokenManager;

    @InjectMocks
    UserService userService;

    User dummyUser;
    Address dummyAddress;
    Payment dummyPayment;
    Image dummyImage;

    String fakeEmail = "empty@gmail.com";
    String fakePassword = "12345AAABBB!@#";
    String fakeCity = "AAA";
    String fakeCountry = "BBB";
    String fakeState = "CCC";
    String fakeStreet = "000 ABC";
    String fakeZipCode = "-ab12345";
    String fakeToken = "";
    String fakeCcNumber = "123";
    String fakeExpPeriod = "aaa";
    String fakeSvcCode = "zzz";
    String fakePicName = "aaa";
    String fakePicType = "dfdf";
    byte[] fakePicByte = {111};

    int fakeId = -1;
    int fakePicId = 1;


//    INIT TEST METHODS
    @BeforeEach
    void setUp() throws Exception {
        this.dummyUser = new User(1, "test@gmail.com", "12345", "Kai",
                "Freedom", "9999999999", null, null, null);
        this.dummyAddress = new Address(1, "5th Avenue", "Manhattan", "NY",
                "United States", "12345", null);
        this.dummyPayment = new Payment(1, "123456789", "01142022", "12345",
                "777", null);
        this.dummyImage = new Image(1, "dog", "png", new byte[111111], null);
    }

    @AfterEach
    void tearDown() throws Exception {
        this.dummyUser = null;
        this.dummyAddress = null;
        this.dummyPayment = null;
        this.dummyImage = null;
    }

//    JUNIT TESTS
//    PROFILE
    @Test
    void testFindByCredentials() {
        String email = this.dummyUser.getUserEmail();
        String password = this.dummyUser.getUserPassword();

        given(this.mockUserRepository.findByUserEmailAndUserPassword(email, password))
                .willReturn(Optional.of(this.dummyUser));

        User expected = this.dummyUser;
        User actual = this.userService.findByCredentials(email, password);
        assertEquals(expected, actual);
        verify(this.mockUserRepository, times(1))
                .findByUserEmailAndUserPassword(email, password);
    }

    @Test
    void testFindByCredentials_Failure_UserNotFound() {
         given(this.mockUserRepository.findByUserEmailAndUserPassword(this.fakeEmail, this.fakePassword))
                .willReturn(Optional.empty());

        User actual = this.userService.findByCredentials(this.fakeEmail, this.fakePassword);
        assertNull(actual);
    }

    @Test
    void testGetByEmail() {
        String email = this.dummyUser.getUserEmail();

        given(this.mockUserRepository.getByEmail(email)).willReturn(Optional.of(this.dummyUser));

        User expected = this.dummyUser;
        User actual = this.userService.getByEmail(email);
        assertEquals(expected, actual);
        verify(this.mockUserRepository, times(1)).getByEmail(email);
    }

    @Test
    void testGetByEmail_Failure_UserNotFound() {
        given(this.mockUserRepository.getByEmail(this.fakeEmail)).willReturn(Optional.empty());

        User actual = this.userService.getByEmail(this.fakeEmail);
        assertNull(actual);
    }

    @Test
    void testGetByEmailAndId() {
        String email = this.dummyUser.getUserEmail();
        int id = this.dummyUser.getUserId();

        given(this.mockUserRepository.getByEmailAndId(email, id)).willReturn(Optional.of(this.dummyUser));

        User expected = this.dummyUser;
        User actual = this.userService.getByEmailAndId(email, id);
        assertEquals(expected, actual);
        verify(this.mockUserRepository, times(1)).getByEmailAndId(email, id);
    }

    @Test
    void testGetByEmailAndId_Failure_UserNotFound() {
        given(this.mockUserRepository.getByEmailAndId(this.fakeEmail, this.fakeId)).willReturn(Optional.empty());

        User actual = this.userService.getByEmailAndId(this.fakeEmail, this.fakeId);
        assertNull(actual);
    }

    @Test
    void testAddNewUser() {
        User user = this.dummyUser;

        given(this.mockUserRepository.addNewUser(user.getFirstName(), user.getLastName(), "",
                user.getUserEmail(), user.getUserPassword())).willReturn(Optional.of(this.dummyUser));

        User expected = this.dummyUser;
        User actual = this.userService.addNewUser(user);
        assertEquals(expected, actual);
        verify(this.mockUserRepository, times(1)).addNewUser(user.getFirstName(), user.getLastName(), "",
                user.getUserEmail(), user.getUserPassword());
    }

    @Test
    void testAddNewUser_Failure_UserNotAdded() {
        User user = this.dummyUser;
        user.setUserEmail("");
        user.setUserPassword("");

        given(this.mockUserRepository.addNewUser(user.getFirstName(), user.getLastName(), "",
                user.getUserEmail(), user.getUserPassword())).willReturn(Optional.empty());

        User actual = this.userService.addNewUser(user);
        assertNull(actual);
    }

    @Test
    void testRemoveToken() {
        String email = this.dummyUser.getUserEmail();
        int id = this.dummyUser.getUserId();
        this.dummyUser.setTokenId("");

        given(this.mockUserRepository.removeToken(email, id, ""))
                .willReturn(Optional.of(this.dummyUser));

        User expected = this.dummyUser;
        User actual = this.userService.removeToken(email, id);
        assertEquals(expected, actual);
        verify(this.mockUserRepository, times(1)).removeToken(email, id, "");
    }

    @Test
    void testRemoveToken_Failure_TokenNotRemoved() {
        given(this.mockUserRepository.removeToken(this.fakeEmail, this.fakeId, ""))
                .willReturn(Optional.empty());

        User actual = this.userService.removeToken(this.fakeEmail, this.fakeId);
        assertNull(actual);
    }

    @Test
    void testAddToken() {
        String email = this.dummyUser.getUserEmail();
        String token = this.dummyUser.getTokenId();
        int id = this.dummyUser.getUserId();

        given(this.mockUserRepository.addToken(email, id, token))
                .willReturn(Optional.of(this.dummyUser));

        User expected = this.dummyUser;
        User actual = this.userService.addToken(email, id, token);
        assertEquals(expected, actual);
        verify(this.mockUserRepository, times(1)).addToken(email, id, token);
    }

    @Test
    void testAddToken_Failure_TokenNotAdded() {
        given(this.mockUserRepository.addToken(this.fakeEmail, this.fakeId, this.fakeToken))
                .willReturn(Optional.empty());

        User actual = this.userService.addToken(this.fakeEmail, this.fakeId, this.fakeToken);
        assertNull(actual);
    }

    @Test
    void testSetNewPassword() {
        String email = this.dummyUser.getUserEmail();
        String password = this.dummyUser.getUserPassword();
        int id = this.dummyUser.getUserId();

        given(this.mockUserRepository.setNewPassword(email, id, password))
                .willReturn(Optional.of(this.dummyUser));

        User expected = this.dummyUser;
        User actual = this.userService.setNewPassword(email, id, password);
        assertEquals(expected, actual);
        verify(this.mockUserRepository, times(1)).setNewPassword(email, id, password);
    }

    @Test
    void testSetNewPassword_Failure_NewPasswordNotSet() {
        given(this.mockUserRepository.setNewPassword(this.fakeEmail,this.fakeId, this.fakePassword))
                .willReturn(Optional.empty());

        User actual = this.userService.setNewPassword(this.fakeEmail,this.fakeId, this.fakePassword);
        assertNull(actual);
    }

//    ADDRESS
    @Test
    void testUpdateAddressById() {
        Address address = this.dummyAddress;
        User user = this.dummyUser;

        given(this.mockAddressRepository.updateAddressById(address.getCity(), address.getCountry(),
                address.getState(), address.getStreet(), address.getZipCode(), user.getUserId()))
                .willReturn(Optional.of(this.dummyAddress));

        Address expected = this.dummyAddress;
        Address actual = this.userService.updateAddressById(address, user.getUserId());
        assertEquals(expected, actual);
        verify(this.mockAddressRepository, times(1)).updateAddressById(address.getCity(),
                address.getCountry(), address.getState(), address.getStreet(), address.getZipCode(), user.getUserId());
    }

    @Test
    void testUpdateAddressById_Failure_AddressNotUpdatedById() {
        Address address = this.setFakeAddress(this.dummyAddress);
        User user = this.dummyUser;
        user.setUserId(this.fakeId);

        given(this.mockAddressRepository.updateAddressById(address.getCity(), address.getCountry(),
                address.getState(), address.getStreet(), address.getZipCode(), user.getUserId()))
                .willReturn(Optional.empty());

        Address actual = this.userService.updateAddressById(address, user.getUserId());
        assertNull(actual);
    }

    @Test
    void testAddNewAddress() {
        Address address = this.dummyAddress;
        User user = this.dummyUser;

        given(this.mockAddressRepository.addNewAddress(address.getCity(), address.getCountry(),
                address.getState(), address.getStreet(), address.getZipCode(), user.getUserId()))
                .willReturn(Optional.of(this.dummyAddress));

        Address expected = this.dummyAddress;
        Address actual = this.userService.addNewAddress(address, user.getUserId());
        assertEquals(expected, actual);
        verify(this.mockAddressRepository, times(1)).addNewAddress(address.getCity(),
                address.getCountry(), address.getState(), address.getStreet(), address.getZipCode(), user.getUserId());
    }

    @Test
    void testAddNewAddress_Failure_AddressNotAdded() {
        Address address = this.setFakeAddress(this.dummyAddress);
        User user = this.dummyUser;
        user.setUserId(this.fakeId);

        given(this.mockAddressRepository.addNewAddress(address.getCity(), address.getCountry(),
                address.getState(), address.getStreet(), address.getZipCode(), user.getUserId()))
                .willReturn(Optional.empty());

        Address actual = this.userService.addNewAddress(address, user.getUserId());
        assertNull(actual);
    }

    @Test
    void testGetAddressById() {
        int id = this.dummyUser.getUserId();

        given(this.mockAddressRepository.getAddressById(id)).willReturn(Optional.of(this.dummyAddress));

        Address expected = this.dummyAddress;
        Address actual = this.userService.getAddressById(id);
        assertEquals(expected, actual);
        verify(this.mockAddressRepository, times(1)).getAddressById(id);
    }

    @Test
    void testGetAddressById_Failure_AddressNotFoundById() {
        given(this.mockAddressRepository.getAddressById(this.fakeId)).willReturn(Optional.empty());

        Address actual = this.userService.getAddressById(this.fakeId);
        assertNull(actual);
    }

//    PAYMENT
    @Test
    void testUpdatePaymentById() {
        Payment payment = this.dummyPayment;
        User user = this.dummyUser;

        given(this.mockPaymentRepository.updatePaymentById(payment.getCcNumber(), payment.getExpPeriod(),
                payment.getSvcCode(), payment.getZipCode(), user.getUserId()))
                .willReturn(Optional.of(this.dummyPayment));

        Payment expected = this.dummyPayment;
        Payment actual = this.userService.updatePaymentById(payment, user.getUserId());
        assertEquals(expected, actual);
        verify(this.mockPaymentRepository, times(1)).updatePaymentById(payment.getCcNumber(),
                payment.getExpPeriod(), payment.getSvcCode(), payment.getZipCode(), user.getUserId());
    }

    @Test
    void testUpdatePaymentById_Failure_PaymentNotUpdatedById() {
        Payment payment = this.setFakePayment(this.dummyPayment);
        User user = this.dummyUser;
        user.setUserId(this.fakeId);

        given(this.mockPaymentRepository.updatePaymentById(payment.getCcNumber(), payment.getExpPeriod(),
                payment.getSvcCode(), payment.getZipCode(), user.getUserId()))
                .willReturn(Optional.empty());

        Payment actual = this.userService.updatePaymentById(payment, user.getUserId());
        assertNull(actual);
    }

    @Test
    void testAddNewPayment() {
        Payment payment = this.dummyPayment;
        User user = this.dummyUser;

        given(this.mockPaymentRepository.addNewPayment(payment.getCcNumber(), payment.getExpPeriod(),
                payment.getSvcCode(), payment.getZipCode(), user.getUserId()))
                .willReturn(Optional.of(this.dummyPayment));

        Payment expected = this.dummyPayment;
        Payment actual = this.userService.addNewPayment(payment, user.getUserId());
        assertEquals(expected, actual);
        verify(this.mockPaymentRepository, times(1)).addNewPayment(payment.getCcNumber(),
                payment.getExpPeriod(), payment.getSvcCode(), payment.getZipCode(), user.getUserId());
    }

    @Test
    void testAddNewPayment_Failure_PaymentNotAdded() {
        Payment payment = this.setFakePayment(this.dummyPayment);
        User user = this.dummyUser;
        user.setUserId(this.fakeId);

        given(this.mockPaymentRepository.addNewPayment(payment.getCcNumber(), payment.getExpPeriod(),
                payment.getSvcCode(), payment.getZipCode(), user.getUserId()))
                .willReturn(Optional.empty());

        Payment actual = this.userService.addNewPayment(payment, user.getUserId());
        assertNull(actual);
    }

    @Test
    void testGetPaymentById() {
        int id = this.dummyUser.getUserId();

        given(this.mockPaymentRepository.getPaymentById(id))
                .willReturn(Optional.of(this.dummyPayment));

        Payment expected = this.dummyPayment;
        Payment actual = this.userService.getPaymentById(id);
        assertEquals(expected, actual);
        verify(this.mockPaymentRepository, times(1)).getPaymentById(id);
    }

    @Test
    void testGetPaymentById_Failure_PaymentNotFoundById() {
        int id = this.fakeId;

        given(this.mockPaymentRepository.getPaymentById(id))
                .willReturn(Optional.empty());

        Payment actual = this.userService.getPaymentById(id);
        assertNull(actual);
    }

    @Test
    void testDeletePayment() {
        int id = this.dummyUser.getUserId();

        given(this.mockPaymentRepository.deletePayment(id))
                .willReturn(Optional.of(this.dummyPayment));

        Payment expected = this.dummyPayment;
        Payment actual = this.userService.deletePayment(id);
        assertEquals(expected, actual);
        verify(this.mockPaymentRepository, times(1)).deletePayment(id);
    }

    @Test
    void testDeletePayment_Failure_PaymentNotDeletedById() {
        int id = this.fakeId;

        given(this.mockPaymentRepository.deletePayment(id))
                .willReturn(Optional.empty());

        Payment actual = this.userService.deletePayment(id);
        assertNull(actual);
    }

//    VALIDATION
    @Test
    void testValidateSession() {
        String email = this.dummyUser.getUserEmail();
        String token = this.dummyUser.getTokenId();
        int id = this.dummyUser.getUserId();

        given(this.mockTokenManager.parseUserEmailFromToken(token)).willReturn(email);
        given(this.mockTokenManager.parseUserIdFromToken(token)).willReturn(id);
        given(this.mockUserRepository.validateSession(email, id, token))
                .willReturn(Optional.of(this.dummyUser));

        String actualEmail = this.mockTokenManager.parseUserEmailFromToken(token);
        assertEquals(email, actualEmail);

        int actualId = this.mockTokenManager.parseUserIdFromToken(token);
        assertEquals(id, actualId);

        User expectedUser = this.dummyUser;
        User actualUser = this.userService.validateSession(token);
        assertEquals(expectedUser, actualUser);

        verify(this.mockTokenManager, times(2)).parseUserEmailFromToken(token);
        verify(this.mockTokenManager, times(2)).parseUserIdFromToken(token);
        verify(this.mockUserRepository, times(1)).validateSession(email, id, token);
    }

    @Test
    void testValidateSession_Failure_SessionIsNotValid() {
        String token = this.fakeToken;

        given(this.mockTokenManager.parseUserEmailFromToken(token)).willThrow(AuthenticationException.class);
        given(this.mockTokenManager.parseUserIdFromToken(token)).willThrow(AuthenticationException.class);

        try{
            this.mockTokenManager.parseUserEmailFromToken(token);
            fail("Expected AuthenticationException to be thrown");
        } catch (Exception e) {
            assertEquals(AuthenticationException.class, e.getClass());
        }

        try{
            this.mockTokenManager.parseUserIdFromToken(token);
            fail("Expected AuthenticationException to be thrown");
        } catch (Exception e) {
            assertEquals(AuthenticationException.class, e.getClass());
        }

        try{
            this.userService.validateSession(token);
            fail("Expected AuthenticationException to be thrown");
        } catch (Exception e) {
            assertEquals(AuthenticationException.class, e.getClass());
        }
    }

//    IMAGE
    @Test
    void testGetImageById() {
        int id = this.dummyUser.getUserId();

        given(this.mockImageRepository.getImageById(id))
                .willReturn(Optional.of(this.dummyImage));

        Image expected = this.dummyImage;
        Image actual = this.userService.getImageById(id);
        assertEquals(expected, actual);
        verify(this.mockImageRepository, times(1)).getImageById(id);
    }

    @Test
    void testGetImageById_Failure_ImageNotFoundById() {
        given(this.mockImageRepository.getImageById(this.fakeId))
                .willReturn(Optional.empty());

        Image actual = this.userService.getImageById(this.fakeId);
        assertNull(actual);
    }

    @Test
    void testDeleteImageById() {
        int id = this.dummyUser.getUserId();

        given(this.mockImageRepository.deleteImageById(id))
                .willReturn(Optional.of(this.dummyImage));

        Image expected = this.dummyImage;
        Image actual = this.userService.deleteImageById(id);
        assertEquals(expected, actual);
        verify(this.mockImageRepository, times(1)).deleteImageById(id);
    }

    @Test
    void testDeleteImageById_Failure_ImageNotDeletedById() {
        given(this.mockImageRepository.deleteImageById(this.fakeId))
                .willReturn(Optional.empty());

        Image actual = this.userService.deleteImageById(this.fakeId);
        assertNull(actual);
    }

    @Test
    void testUpdateImageById() {
        int id = this.dummyUser.getUserId();
        byte[] picByte = this.dummyImage.getPicByte();
        String picName = this.dummyImage.getPicName();
        String picType = this.dummyImage.getPicType();

        given(this.mockImageRepository.updateImageById(picByte, picName, picType, id))
                .willReturn(Optional.of(this.dummyImage));

        Image expected = this.dummyImage;
        Image actual = this.userService.updateImageById(this.dummyImage, id);
        assertEquals(expected, actual);
        verify(this.mockImageRepository, times(1)).updateImageById(picByte, picName,
                picType, id);
    }

    @Test
    void testUpdateImageById_Failure_ImageNotUpdatedId() {
        Image fakeImage = this.setFakeImage(this.dummyImage);

        given(this.mockImageRepository.updateImageById(fakeImage.getPicByte(),
                fakeImage.getPicName(), fakeImage.getPicType(), this.fakeId))
                .willReturn(Optional.empty());

        Image actual = this.userService.updateImageById(fakeImage, this.fakeId);
        assertNull(actual);
    }

    @Test
    void testAddNewImage() {
        int id = this.dummyUser.getUserId();
        byte[] picByte = this.dummyImage.getPicByte();
        String picName = this.dummyImage.getPicName();
        String picType = this.dummyImage.getPicType();

        given(this.mockImageRepository.addNewImage(picByte, picName, picType, id))
                .willReturn(Optional.of(this.dummyImage));

        Image expected = this.dummyImage;
        Image actual = this.userService.addNewImage(this.dummyImage, id);
        assertEquals(expected, actual);
        verify(this.mockImageRepository, times(1)).addNewImage(picByte, picName,
                picType, id);
    }

    @Test
    void testAddNewImage_Failure_ImageNotAddedId() {
        Image fakeImage = this.setFakeImage(this.dummyImage);

        given(this.mockImageRepository.addNewImage(fakeImage.getPicByte(),
                fakeImage.getPicName(), fakeImage.getPicType(), this.fakeId))
                .willReturn(Optional.empty());

        Image actual = this.userService.addNewImage(fakeImage, this.fakeId);
        assertNull(actual);
    }


//    HELP METHODS
    private Address setFakeAddress(Address address) {
        address.setCity(this.fakeCity);
        address.setStreet(this.fakeStreet);
        address.setCountry(this.fakeCountry);
        address.setState(this.fakeState);
        address.setZipCode(this.fakeZipCode);
        address.setUser(null);
        return address;
    }

    private Payment setFakePayment(Payment payment) {
        payment.setCcNumber(this.fakeCcNumber);
        payment.setExpPeriod(this.fakeExpPeriod);
        payment.setSvcCode(this.fakeSvcCode);
        payment.setZipCode(this.fakeZipCode);
        payment.setUser(null);
        return payment;
    }

    private Image setFakeImage(Image image) {
        image.setPicByte(this.fakePicByte);
        image.setPicId(this.fakePicId);
        image.setPicName(this.fakePicName);
        image.setPicType(this.fakePicType);
        image.setUser(null);
        return image;
    }
}
