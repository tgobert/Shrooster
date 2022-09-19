package com.revature.dtos;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@RequiredArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class LoginRequest {
    @Email
    @NotBlank
    private String userEmail;

    @Length(min = 5, max = 200)
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$")
    private String userPassword;
}
