package org.team3.chatting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.team3.chatting.controllers.RequestChatRoom;
import org.team3.chatting.entities.ChatRoom;
import org.team3.chatting.repositories.ChatRoomRepository;
import org.team3.member.MemberUtil;

@Service
@RequiredArgsConstructor
public class ChatRoomSaveService {

    private final ChatRoomRepository chatRoomRepository;
    private final MemberUtil memberUtil;
    public ChatRoom save(RequestChatRoom form) {
        String roomId = form.getRoomId();

        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseGet(() -> ChatRoom.builder()
                        .roomId(roomId)
                        .member(memberUtil.getMember())
                        .build());

        room.setRoomNm(form.getRoomNm());
        room.setCapacity(form.getCapacity());

        chatRoomRepository.saveAndFlush(room);

        return room;
    }
}