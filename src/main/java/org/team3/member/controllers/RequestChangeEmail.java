package org.team3.member.controllers;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestChangeEmail {
    @NotBlank @Email
    private String newEmail;
}
