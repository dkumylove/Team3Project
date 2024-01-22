package org.team3.board.entities;

import org.team3.member.entities.Member;

public interface AuthCheck {
    boolean isEditable();
    boolean isDeletable();
    boolean isMine();

    Member getMember();
}
