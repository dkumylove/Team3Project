package org.team3.member.controllers;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestFindId{
        @NotBlank @Email
        String email;

        @NotBlank
        String name;
}
