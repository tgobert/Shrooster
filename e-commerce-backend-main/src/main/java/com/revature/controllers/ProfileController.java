package com.revature.controllers;

import com.revature.dtos.*;
import com.revature.exceptions.UnauthorizedSessionException;
import com.revature.models.Address;
import com.revature.models.Image;
import com.revature.models.Payment;
import com.revature.models.User;
import com.revature.services.UserService;
import com.revature.util.JwtTokenManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@RestController
@RequestMapping("/profile")
@CrossOrigin(origins = { "http://localhost:4200",
        "http://localhost:3000", "http://e-commerce-shrooster.s3-website-us-east-1.amazonaws.com" }, allowCredentials = "true", allowedHeaders = "*")
public class ProfileController {
    private UserService userService;
    private JwtTokenManager tokenManager;
    private ModelMapper modelMapper = new ModelMapper();

    // Constructors
    @Autowired
    public ProfileController(UserService userService, JwtTokenManager tokenManager) {
        this.userService = userService;
        this.tokenManager = tokenManager;
    }

    // Methods
    // PROFILE
    @GetMapping
    public ResponseEntity<User> getUserBasicInfo(HttpServletRequest request, HttpServletResponse response) {
        try {
            userService.validateSession(request.getHeader("rolodex-token"));
        } catch (UnauthorizedSessionException e) {
            response.addHeader("error-message", "Provided credentials are incorrect.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new User());
        }

        String userToken = request.getHeader("rolodex-token");
        String userEmail = tokenManager.parseUserEmailFromToken(userToken);
        int userId = tokenManager.parseUserIdFromToken(userToken);
        User user = userService.getByEmailAndId(userEmail, userId);
        if (user != null) {
            response.addHeader("rolodex-token", userToken);
            response.addHeader("Access-Control-Expose-Header", "rolodex-token");
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } else {
            response.addHeader("error-message", "User not found.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new User());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllUserInformation(HttpServletResponse response,
            HttpServletRequest request) {
        try {
            userService.validateSession(request.getHeader("rolodex-token"));
        } catch (UnauthorizedSessionException e) {
            response.addHeader("error-message", "Provided credentials are incorrect.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new HashMap<>());
        }

        int userId = tokenManager.parseUserIdFromToken(request.getHeader("rolodex-token"));
        String userEmail = tokenManager.parseUserEmailFromToken(request.getHeader("rolodex-token"));
        Map<String, Object> allUserInfo = new HashMap<>();
        User user = userService.getByEmail(userEmail);
        Address address = userService.getAddressById(userId);
        Payment payment = userService.getPaymentById(userId);

        if (address != null) {
            address.setUser(null);
        }
        if (payment != null) {
            payment.setUser(null);
        }

        allUserInfo.put("user_profile", user);
        allUserInfo.put("user_address", address);
        allUserInfo.put("user_payment", payment);

        if (allUserInfo.get("user_profile") != null) {
            response.addHeader("rolodex-token", request.getHeader("rolodex-token"));
            response.addHeader("Access-Control-Expose-Header", "rolodex-token");
            return ResponseEntity.status(HttpStatus.OK).body(allUserInfo);
        } else {
            response.addHeader("rolodex-token", request.getHeader("rolodex-token"));
            response.addHeader("error-message", "No data was found.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<>());
        }
    }

    // PASSWORD
    @PatchMapping
    public ResponseEntity<User> setNewPassword(@Valid @RequestBody PasswordRequest passwordDTO,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            userService.validateSession(request.getHeader("rolodex-token"));
        } catch (UnauthorizedSessionException e) {
            response.addHeader("error-message", "Provided credentials are incorrect.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new User());
        }

        User newPassword = modelMapper.map(passwordDTO, User.class);
        String userToken = request.getHeader("rolodex-token");
        String userEmail = tokenManager.parseUserEmailFromToken(userToken);
        int userId = tokenManager.parseUserIdFromToken(userToken);
        User userUpdatedPassword = userService.setNewPassword(userEmail, userId, newPassword.getUserPassword());
        if (userUpdatedPassword != null) {
            response.addHeader("rolodex-token", userToken);
            response.addHeader("Access-Control-Expose-Header", "rolodex-token");
            userUpdatedPassword.setUserPassword(newPassword.getUserPassword());
            return ResponseEntity.status(HttpStatus.OK).body(userUpdatedPassword);
        } else {
            response.addHeader("error-message", "Password is not updated.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new User());
        }
    }

    // ADDRESS
    @PostMapping("/uaddress")
    public ResponseEntity<Address> addAddress(@Valid @RequestBody AddressRequest registerDTO,
            HttpServletResponse response, HttpServletRequest request) {
        try {
            userService.validateSession(request.getHeader("rolodex-token"));
        } catch (UnauthorizedSessionException e) {
            response.addHeader("error-message", "Provided credentials are incorrect.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Address());
        }

        Address address = modelMapper.map(registerDTO, Address.class);
        int userId = tokenManager.parseUserIdFromToken(request.getHeader("rolodex-token"));

        Address newAddress = new Address();
        if (userService.getAddressById(userId) != null) {
            newAddress = userService.updateAddressById(address, userId);
        } else {
            newAddress = userService.addNewAddress(address, userId);
        }

        if (newAddress != null) {
            response.addHeader("rolodex-token", request.getHeader("rolodex-token"));
            response.addHeader("Access-Control-Expose-Header", "rolodex-token");
            return ResponseEntity.status(HttpStatus.OK).body(address);
        } else {
            response.addHeader("error-message", "There is some internal error with saving address.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Address());
        }
    }

    @DeleteMapping("/uaddress")
    public ResponseEntity<Address> deleteAddress(HttpServletResponse response, HttpServletRequest request) {
        try {
            userService.validateSession(request.getHeader("rolodex-token"));
        } catch (UnauthorizedSessionException e) {
            response.addHeader("error-message", "Provided credentials are incorrect.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Address());
        }

        int userId = tokenManager.parseUserIdFromToken(request.getHeader("rolodex-token"));
        userService.deleteAddress(userId);
        response.addHeader("rolodex-token", request.getHeader("rolodex-token"));
        response.addHeader("Access-Control-Expose-Header", "rolodex-token");
        return ResponseEntity.status(HttpStatus.OK).body(new Address());
    }

    @GetMapping("/uaddress")
    public ResponseEntity<Address> getAddress(HttpServletResponse response, HttpServletRequest request) {
        try {
            userService.validateSession(request.getHeader("rolodex-token"));
        } catch (UnauthorizedSessionException e) {
            response.addHeader("error-message", "Provided credentials are incorrect.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Address());
        }

        int userId = tokenManager.parseUserIdFromToken(request.getHeader("rolodex-token"));
        Address existingAddress = userService.getAddressById(userId);
        if (existingAddress != null) {
            existingAddress.setUser(null);
            response.addHeader("rolodex-token", request.getHeader("rolodex-token"));
            response.addHeader("Access-Control-Expose-Header", "rolodex-token");
            return ResponseEntity.status(HttpStatus.OK).body(existingAddress);
        } else {
            response.addHeader("error-message", "No data about address.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Address());
        }
    }

    // PAYMENT
    @PostMapping("/upayment")
    public ResponseEntity<Payment> addPayment(@Valid @RequestBody PaymentRequest paymentDTO,
            HttpServletResponse response, HttpServletRequest request) {
        try {
            userService.validateSession(request.getHeader("rolodex-token"));
        } catch (UnauthorizedSessionException e) {
            response.addHeader("error-message", "Provided credentials are incorrect.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Payment());
        }

        Payment payment = modelMapper.map(paymentDTO, Payment.class);
        int userId = tokenManager.parseUserIdFromToken(request.getHeader("rolodex-token"));

        Payment newPayment = new Payment();
        if (userService.getPaymentById(userId) != null) {
            newPayment = userService.updatePaymentById(payment, userId);
        } else {
            newPayment = userService.addNewPayment(payment, userId);
        }

        if (newPayment != null) {
            response.addHeader("rolodex-token", request.getHeader("rolodex-token"));
            response.addHeader("Access-Control-Expose-Header", "rolodex-token");
            return ResponseEntity.status(HttpStatus.OK).body(payment);
        } else {
            response.addHeader("error-message", "There is some internal error with saving address.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Payment());
        }
    }

    @GetMapping("/upayment")
    public ResponseEntity<Payment> getPayment(HttpServletResponse response, HttpServletRequest request) {
        try {
            userService.validateSession(request.getHeader("rolodex-token"));
        } catch (UnauthorizedSessionException e) {
            response.addHeader("error-message", "Provided credentials are incorrect.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Payment());
        }

        int userId = tokenManager.parseUserIdFromToken(request.getHeader("rolodex-token"));
        Payment currentPayment = userService.getPaymentById(userId);
        if (currentPayment != null) {
            currentPayment.setUser(null);
            response.addHeader("rolodex-token", request.getHeader("rolodex-token"));
            response.addHeader("Access-Control-Expose-Header", "rolodex-token");
            return ResponseEntity.status(HttpStatus.OK).body(currentPayment);
        } else {
            response.addHeader("rolodex-token", request.getHeader("rolodex-token"));
            response.addHeader("error-message", "There is some internal error with saving address.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Payment());
        }
    }

    @DeleteMapping("/upayment")
    public ResponseEntity<Payment> deletePayment(HttpServletResponse response, HttpServletRequest request) {
        try {
            userService.validateSession(request.getHeader("rolodex-token"));
        } catch (UnauthorizedSessionException e) {
            response.addHeader("error-message", "Provided credentials are incorrect.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Payment());
        }

        int userId = tokenManager.parseUserIdFromToken(request.getHeader("rolodex-token"));
        userService.deletePayment(userId);
        response.addHeader("rolodex-token", request.getHeader("rolodex-token"));
        response.addHeader("Access-Control-Expose-Header", "rolodex-token");
        return ResponseEntity.status(HttpStatus.OK).body(new Payment());
    }

    // IMAGE
    @PostMapping("/image")
    public ResponseEntity<Image> addImage(@RequestParam("imageFile") MultipartFile file,
            HttpServletResponse response,
            HttpServletRequest request) throws IOException {
        try {
            userService.validateSession(request.getHeader("rolodex-token"));
        } catch (UnauthorizedSessionException e) {
            response.addHeader("error-message", "Provided credentials are incorrect.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Image());
        }

        Image image = new Image();
        image.setPicName(file.getOriginalFilename());
        image.setPicType(file.getContentType());
        image.setPicByte(compressBytes(file.getBytes()));

        int userId = tokenManager.parseUserIdFromToken(request.getHeader("rolodex-token"));
        Image newImage = new Image();
        if (userService.getImageById(userId) != null) {
            newImage = userService.updateImageById(image, userId);
        } else {
            newImage = userService.addNewImage(image, userId);
        }

        if (newImage != null) {
            newImage.setUser(null);
            response.addHeader("rolodex-token", request.getHeader("rolodex-token"));
            response.addHeader("Access-Control-Expose-Header", "rolodex-token");
            return ResponseEntity.status(HttpStatus.OK).body(newImage);
        } else {
            response.addHeader("error-message", "There is some internal error with saving image.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Image());
        }
    }

    @GetMapping("/image")
    public ResponseEntity<Image> getImage(HttpServletResponse response, HttpServletRequest request) throws IOException {
        try {
            userService.validateSession(request.getHeader("rolodex-token"));
        } catch (UnauthorizedSessionException e) {
            response.addHeader("error-message", "Provided credentials are incorrect.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Image());
        }

        int userId = tokenManager.parseUserIdFromToken(request.getHeader("rolodex-token"));
        Image currentImage = userService.getImageById(userId);
        if (currentImage != null) {
            Image uncompressedImage = new Image();
            uncompressedImage.setPicName(currentImage.getPicName());
            uncompressedImage.setPicType(currentImage.getPicType());
            uncompressedImage.setPicByte(decompressBytes(currentImage.getPicByte()));
            uncompressedImage.setPicId(currentImage.getPicId());
            response.addHeader("rolodex-token", request.getHeader("rolodex-token"));
            response.addHeader("Access-Control-Expose-Header", "rolodex-token");
            return ResponseEntity.status(HttpStatus.OK).body(uncompressedImage);
        } else {
            response.addHeader("rolodex-token", request.getHeader("rolodex-token"));
            response.addHeader("error-message", "There is some internal error with retrieving image.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Image());
        }
    }

    @DeleteMapping("/image")
    public ResponseEntity<Image> deleteImage(HttpServletResponse response,
            HttpServletRequest request) throws IOException {
        try {
            userService.validateSession(request.getHeader("rolodex-token"));
        } catch (UnauthorizedSessionException e) {
            response.addHeader("error-message", "Provided credentials are incorrect.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Image());
        }

        try {
            int userId = tokenManager.parseUserIdFromToken(request.getHeader("rolodex-token"));
            userService.deleteImageById(userId);
            response.addHeader("rolodex-token", request.getHeader("rolodex-token"));
            response.addHeader("Access-Control-Expose-Header", "rolodex-token");
            return ResponseEntity.status(HttpStatus.OK).body(new Image());
        } catch (Exception e) {
            response.addHeader("rolodex-token", request.getHeader("rolodex-token"));
            response.addHeader("error-message", "There is some internal error with retrieving image.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Image());
        }
    }

    // HELP METHODS
    // TO COMPRESS IMAGE BYTES BEFORE STORING INTO DB
    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }

        try {
            outputStream.close();
        } catch (IOException e) {
        }
        return outputStream.toByteArray();
    }

    // UNCOMPRESS IMAGE BYTES BEFORE RETURNING INTO ANGULAR APPLICATION
    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];

        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException e) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }
}
