package com.revature.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

@RequiredArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PasswordRequest {
    @Length(min = 5, max = 200)
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$")
    private String userPassword;
}
