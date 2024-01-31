package org.team3.admin.option.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.team3.commons.entities.Base;
import org.team3.member.Authority;
import org.team3.member.entities.Member;

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


}
