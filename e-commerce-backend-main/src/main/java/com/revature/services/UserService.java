package com.revature.services;

import com.revature.exceptions.UnauthorizedSessionException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Address;
import com.revature.models.Image;
import com.revature.models.Payment;
import com.revature.models.User;
import com.revature.repositories.AddressRepository;
import com.revature.repositories.ImageRepository;
import com.revature.repositories.PaymentRepository;
import com.revature.repositories.UserRepository;
import com.revature.util.JwtTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final PaymentRepository paymentRepository;
    private final ImageRepository imageRepository;
    private JwtTokenManager tokenManager;

    //    Constructors
    @Autowired
    public UserService(UserRepository userRepository, AddressRepository addressRepository, PaymentRepository paymentRepository, ImageRepository imageRepository, JwtTokenManager tokenManager) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.paymentRepository = paymentRepository;
        this.imageRepository = imageRepository;
        this.tokenManager = tokenManager;
    }

    //    Methods
//    USER
    @Transactional
    public User findByCredentials(String email, String password) {
        Optional<User> existingUser = userRepository.findByUserEmailAndUserPassword(email, password);
        return existingUser.orElse(null);
    }

    @Transactional
    public User getByEmail(String userEmail) {
        Optional<User> user = userRepository.getByEmail(userEmail);
        return user.orElse(null);
    }

    @Transactional
    public User getByEmailAndId(String userEmail, int userId) {
        Optional<User> user = userRepository.getByEmailAndId(userEmail, userId);
        return user.orElse(null);
    }

    @Transactional
    public User addNewUser(User user) {
        Optional<User> addedUser = userRepository.addNewUser(user.getFirstName(), user.getLastName(),
                "", user.getUserEmail(), user.getUserPassword());
        return addedUser.orElse(null);
    }

    @Transactional
    public User removeToken(String userEmail, int userId) {
        Optional<User> addedUser = userRepository.removeToken(userEmail, userId, "");
        return addedUser.orElse(null);
    }

    @Transactional
    public User addToken(String userEmail, int userId, String newToken) {
        Optional<User> addedUser = userRepository.addToken(userEmail, userId, newToken);
        return addedUser.orElse(null);
    }

    @Transactional
    public User setNewPassword(String userEmail, int userId, String newPassword) {
        Optional<User> addedUser = userRepository.setNewPassword(userEmail, userId, newPassword);
        return addedUser.orElse(null);
    }

    @Transactional(readOnly = true)
    public User findByUserId(int userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found."));
    }

    //    ADDRESS
    @Transactional
    public Address updateAddressById(Address address, int userId) {
        Optional<Address> updatedAddress = addressRepository.updateAddressById(address.getCity(), address.getCountry(),
                address.getState(), address.getStreet(), address.getZipCode(), userId);
        return updatedAddress.orElse(null);
    }

    @Transactional
    public Address addNewAddress(Address address, int userId) {
        Optional<Address> addedAddress = addressRepository.addNewAddress(address.getCity(), address.getCountry(),
                address.getState(), address.getStreet(), address.getZipCode(), userId);
        return addedAddress.orElse(null);
    }

    @Transactional
    public Address getAddressById(int userId) {
        Optional<Address> address = addressRepository.getAddressById(userId);
        return address.orElse(null);
    }

    @Transactional
    public Address deleteAddress(int userId) {
        Optional<Address> deletedAddress = addressRepository.deleteAddress(userId);
        return deletedAddress.orElse(null);
    }


    //    PAYMENT
    @Transactional
    public Payment updatePaymentById(Payment payment, int userId) {
        Optional<Payment> updatedPayment = paymentRepository.updatePaymentById(payment.getCcNumber(),
                payment.getExpPeriod(), payment.getSvcCode(), payment.getZipCode(), userId);
        return updatedPayment.orElse(null);
    }

    @Transactional
    public Payment addNewPayment(Payment payment, int userId) {
        Optional<Payment> addedPayment = paymentRepository.addNewPayment(payment.getCcNumber(),
                payment.getExpPeriod(), payment.getSvcCode(), payment.getZipCode(), userId);
        return addedPayment.orElse(null);
    }

    @Transactional
    public Payment getPaymentById(int userId) {
        Optional<Payment> payment = paymentRepository.getPaymentById(userId);
        return payment.orElse(null);
    }

    @Transactional
    public Payment deletePayment(int userId) {
        Optional<Payment> deletedPayment = paymentRepository.deletePayment(userId);
        return deletedPayment.orElse(null);
    }

    //    VALIDATION
    @Transactional
    public User validateSession(String currentToken) {
        String userEmail = tokenManager.parseUserEmailFromToken(currentToken);
        int userId = tokenManager.parseUserIdFromToken(currentToken);
        Optional<User> existingSession = userRepository.validateSession(userEmail, userId, currentToken);
        return existingSession.orElseThrow(() -> new UnauthorizedSessionException("Session is expired. Please sign-in first."));
    }

    //    IMAGE
    @Transactional
    public Image getImageById(int userId) {
        Optional<Image> image = imageRepository.getImageById(userId);
        return image.orElse(null);
    }

    @Transactional
    public Image deleteImageById(int userId) {
        Optional<Image> image = imageRepository.deleteImageById(userId);
        return image.orElse(null);
    }

    @Transactional
    public Image updateImageById(Image image, int userId) {
        Optional<Image> updatedImage = imageRepository.updateImageById(image.getPicByte(), image.getPicName(),
                image.getPicType(), userId);
        return updatedImage.orElse(null);
    }

    @Transactional
    public Image addNewImage(Image image, int userId) {
        Optional<Image> addedImage = imageRepository.addNewImage(image.getPicByte(), image.getPicName(),
                image.getPicType(), userId);
        return addedImage.orElse(null);
    }

}