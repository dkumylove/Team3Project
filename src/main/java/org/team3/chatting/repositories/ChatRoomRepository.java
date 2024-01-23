package org.team3.chatting.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.team3.chatting.entities.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String>, QuerydslPredicateExecutor {

}