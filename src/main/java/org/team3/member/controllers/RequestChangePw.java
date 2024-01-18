package org.team3.member.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestChangePw {

    @NotBlank
    private String cntpwd;
    @NotBlank
    private String newpwd;
    @NotBlank
    private String checkpwd;
}
