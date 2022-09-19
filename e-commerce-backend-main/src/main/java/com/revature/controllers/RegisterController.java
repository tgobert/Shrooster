package com.revature.controllers;

import com.revature.dtos.RegisterRequest;
import com.revature.models.User;
import com.revature.services.UserService;
import com.revature.util.JwtTokenManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/register")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:3000", "http://e-commerce-shrooster.s3-website-us-east-1.amazonaws.com" }, allowCredentials = "true",
        allowedHeaders = "*")
public class RegisterController {
    private UserService userService;
    private JwtTokenManager tokenManager;
    private ModelMapper modelMapper = new ModelMapper();

//    Constructors
    @Autowired
    public RegisterController(UserService userService, JwtTokenManager tokenManager) {
        this.userService = userService;
        this.tokenManager = tokenManager;
    }

//    Methods
    @PostMapping
    public ResponseEntity<User> addUser(@Valid @RequestBody RegisterRequest registerDTO,
                                        HttpServletResponse response) {
        User user = modelMapper.map(registerDTO, User.class);
        if (userService.getByEmail(user.getUserEmail()) != null) {
            response.addHeader("error-message", "An email '" + user.getUserEmail() + "' is already in use. Please choose another one.");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new User());
        }

        User addedUser = userService.addNewUser(user);
        if (addedUser != null) {
            String newToken = tokenManager.issueToken(addedUser);
            User updatedToken = userService.addToken(addedUser.getUserEmail(), addedUser.getUserId(), newToken);

            if (updatedToken != null) {
                addedUser.setTokenId(newToken);
                response.addHeader("rolodex-token", newToken);
                response.addHeader("Access-Control-Expose-Header", "rolodex-token");
                return ResponseEntity.status(HttpStatus.CREATED).body(addedUser);
            } else {
                response.addHeader("error-message", "Session has expired. Please sign-in again.");
                return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(new User());
            }
        } else {
            response.addHeader("error-message", "Internal Server Error.");
            response.setStatus(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new User());
        }
    }


}
