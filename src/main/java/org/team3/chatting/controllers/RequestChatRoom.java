package org.team3.chatting.controllers;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 커맨드객체
 */
@Data
public class RequestChatRoom {

    @NotBlank
    private String roomId;

    @NotBlank
    private String roomNm;

    @Size(min=2)
    private int capacity = 2;
}