package org.team3.member.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Follow {

    @Id
    @GeneratedValue
    @Column(name = "follow_id")
    private Long id;

    /* 팔로워 */
    @ManyToOne
    @JoinColumn(name = "from_member")
    private Member fromMember;

    /* 팔로잉 */
    @ManyToOne
    @JoinColumn(name = "to_member")
    private Member toMember;

}
