package org.team3.admin.member.controllers;


import lombok.Data;
import org.team3.member.Authority;
import org.team3.member.controllers.MemberSearch;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class MemberSearchOptions extends MemberSearch {

    private LocalDateTime createSdate;
    private LocalDateTime createEdate;
    private Authority authority;
    private String userId;

    private boolean active;
}
