package org.team3.admin.config.controllers;

import lombok.Data;

@Data
public class BasicConfig {

    private String siteTitle = "";
    private String siteDescription = "";
    private String siteKeywords = "";
    private int cssJsVersion = 1;
    private String joinTerms = "약관 내용";
    private String thumbSize = "";

}