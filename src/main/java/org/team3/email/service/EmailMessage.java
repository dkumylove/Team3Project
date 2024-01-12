package org.team3.email.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 이메일 데이터 클래스
 * */
@Data @AllArgsConstructor @NoArgsConstructor
public class EmailMessage{
        String to;
        String subject;
        String message;
        }
