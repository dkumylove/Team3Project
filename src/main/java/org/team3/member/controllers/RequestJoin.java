package org.team3.member.controllers;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

/**
 * 커맨드 객체는 보통 controller 패키지에 있음
 */
@Data
public class RequestJoin {

    private String gid = UUID.randomUUID().toString();

    @NotBlank @Email
    private String email;

    @NotBlank @Size(min=6)
    private String userId;

    @NotBlank @Size(min = 8)
    private String password;

    @NotBlank
    private String confirmPassword;

    @NotBlank
    private String name;

    @NotBlank
    private String nickName;

    @AssertTrue
    private boolean agree;

    @NotEmpty
    private Set<String> option;
}
