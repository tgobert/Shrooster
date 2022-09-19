package com.revature.controllers;

import com.revature.dtos.LoginRequest;
import com.revature.models.User;
import com.revature.services.UserService;
import com.revature.util.JwtTokenManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:3000", "http://e-commerce-shrooster.s3-website-us-east-1.amazonaws.com" }, allowCredentials = "true",
        allowedHeaders = "*")
public class AuthController {

    private UserService userService;
    private JwtTokenManager tokenManager;
    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public AuthController(UserService userService, JwtTokenManager tokenManager) {
        this.userService = userService;
        this.tokenManager = tokenManager;
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@Valid @RequestBody LoginRequest loginDTO, HttpServletResponse response) {
        User user = modelMapper.map(loginDTO, User.class);
        User existingUser = userService.findByCredentials(user.getUserEmail(), user.getUserPassword());

        if (existingUser != null) {
            String newToken = tokenManager.issueToken(existingUser);
            User updatedToken = userService.addToken(existingUser.getUserEmail(), existingUser.getUserId(), newToken);
            if (updatedToken != null) {
                existingUser.setTokenId(newToken);
                response.addHeader("rolodex-token", newToken);
                response.addHeader("Access-Control-Expose-Header", "rolodex-token");
                return ResponseEntity.status(HttpStatus.CREATED).body(existingUser);
            } else {
                response.addHeader("error-message", "Token was not saved in DB. Please try it again later.");
                return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(new User());
            }
        } else {
            response.addHeader("error-message", "Provided credentials are incorrect.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new User());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<User> logout(HttpServletRequest request, HttpServletResponse response) {
        String userToken = request.getHeader("rolodex-token");
        String userEmail = tokenManager.parseUserEmailFromToken(userToken);
        int userId = tokenManager.parseUserIdFromToken(userToken);

        User existingUser = userService.removeToken(userEmail, userId);
        if (existingUser != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new User());
        } else {
            response.addHeader("error-message", "Internal error. Please try it again later.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new User());
        }
    }


}
