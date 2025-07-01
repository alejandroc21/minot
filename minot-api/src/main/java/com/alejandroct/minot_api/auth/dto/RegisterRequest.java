package com.alejandroct.minot_api.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import static com.alejandroct.minot_api.constants.ValidationMessage.*;

public record RegisterRequest(

        @NotBlank(message = EMAIL_REQUIRED)
        @Email(message = VALID_EMAIL)
        String email,
        @NotBlank(message = PASSWORD_REQUIRED)
        String password
) {
}
