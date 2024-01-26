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

//    @Id @GeneratedValue
//    private String seq;

//    @Column(length = 65, nullable = false)
//    private String gid = UUID.randomUUID().toString();

    @Id
    private String optionname;

//    @Lob
//    private String details;

    private String category;


}
