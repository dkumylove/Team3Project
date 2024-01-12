package org.team3.member.controllers;

import lombok.Data;

@Data
public class RequestChangePw {
    private String cntpwd;
    private String newpwd;
    private String checkpwd;
}
