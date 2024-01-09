package org.team3.entity.board;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BoardViewId {

    /**
     * @EqualsAndHashCode : equals와 hashCode 메소드를 자동으로 생성
     * @NoArgsConstructor : 파라미터가 없는 기본 생성자를 생성
     * @AllArgsConstructor : 모든 필드를 파라미터로 받는 생성자를 생성
     */

    private Long seq;
    private Integer uid;
}
