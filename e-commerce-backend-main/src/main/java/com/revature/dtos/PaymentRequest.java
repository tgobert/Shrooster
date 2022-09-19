package com.revature.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@RequiredArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PaymentRequest {
    @NotBlank
    @Pattern(regexp = "^[\\d]+$")
    private String ccNumber;

    @NotBlank
    private String expPeriod;

    @NotBlank
    @Pattern(regexp = "^[\\d]+$")
    private String svcCode;

    @NotBlank
    @Pattern(regexp = "^[\\d]+$")
    private String zipCode;
}
