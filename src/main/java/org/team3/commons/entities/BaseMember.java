package org.team3.commons.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseMember extends Base {

    /**
     * @EntityListeners : Spring Data JPA에서 엔터티 감시를 위한 애노테이션
     * AuditingEntityListener
     *    : pring Data JPA가 제공하는 리스너
     *    : 엔터티의 변경 사항을 감지하고 이에 대한 자동 감사(Auditing)를 수행
     *    : 생성일(Audit for Created Date): 엔터티가 처음으로 저장될 때 생성일을 자동으로 기록
     *    : 수정일(Audit for Last Modified Date): 엔터티가 수정될 때 수정일을 자동으로 갱신
     *
     * @CreatedBy : 엔터티가 처음으로 저장될 때 해당 필드에 엔터티를 생성한 사용자의 정보를 자동으로 설정
     * @LastModifiedBy : 엔터티가 수정될 때 해당 필드에 마지막으로 엔터티를 수정한 사용자의 정보를 자동으로 갱신
     */
    @CreatedBy
    @Column(length = 40, updatable = false)  // 업데이트할때 변경되면 안됨
    private String createdBy;

    @LastModifiedBy
    @Column(length = 40, insertable = false) // 처음등록할떄 들어가면 안됨
    private String modifiedBy;

}