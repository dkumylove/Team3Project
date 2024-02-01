package org.team3.admin.option.entities;

import jakarta.persistence.*;
import lombok.*;
import org.team3.commons.entities.Base;
import org.team3.member.Authority;
import org.team3.member.entities.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Options extends Base {

    @Id
    private String optionname;
    private String category;
    private boolean active=true;

    @ManyToMany(mappedBy = "option")
    @ToString.Exclude
    private List<Member> members = new ArrayList<>();
    public int getMemberCount() {
        return this.members.size();
    }

}
