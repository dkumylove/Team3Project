package org.team3.email.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 이메일 메세지 데이터 클래스
 * @param to : 수신인
 * @param subject : 제목
 * @param message : 내용
 */
@Data @AllArgsConstructor @NoArgsConstructor
public class EmailMessage{
        String to;
        String subject;
        String message;
        }
