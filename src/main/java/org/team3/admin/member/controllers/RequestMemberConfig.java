package org.team3.admin.member.controllers;

import jakarta.persistence.Transient;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.team3.file.entities.FileInfo;
import org.team3.member.Authority;

import java.util.List;
import java.util.UUID;

@Data
public class RequestMemberConfig {
    private String mode = "add";

    private int listOrder;

    @NotBlank
    private String userId;

    @NotBlank
    private String name;

    private String password;

    private boolean act;

    @Email
    private String email;

    private String nickName;

    private String gid = UUID.randomUUID().toString();

    private String managerType;; // 관리자인지 아닌지

    @Transient  // 내부사용목적
    private FileInfo profileImage;   // path, url

    @AssertTrue
    private boolean agree; // 약관동의 여부

    private String[] options; // 보조지표

}
