package org.team3.member.repositories;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.Nullable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.team3.admin.option.entities.Options;
import org.team3.member.entities.Member;
import org.team3.member.entities.QMember;

import java.util.List;
import java.util.Optional;

import static org.team3.member.entities.QMember.member;

public interface MemberRepository extends JpaRepository<Member, Long>, QuerydslPredicateExecutor<Member> {
    @EntityGraph(attributePaths = "authorities")
    Optional<Member> findByEmail(String email);

    @EntityGraph(attributePaths = "authorities")
    Optional<Member> findByUserId(String userId);

    @Query("SELECT m.option FROM Member m WHERE m.userId = :userId")
    List<Options> findByUserIdWithOption(@Param("userId") String userId);

    default boolean existsByEmail(String email) {
        QMember member = QMember.member;

        return exists(member.email.eq(email));
    }

    default boolean existsByUserId(String userId) {
        QMember member = QMember.member;

        return exists(member.userId.eq(userId));
    }
    /**
     * 이메일과 회원명으로 조회되는지 체크
     *
     * @param email
     * @param name
     * @return
     */
    default boolean existsByEmailAndName(String email, @Nullable String name) {
        QMember member = QMember.member;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(member.email.eq(email));
        if(name!=null) {
            builder.and(member.name.eq(name));
        }
        return exists(builder);
    }

    default boolean existsByEmailAndUserId(String email, String userId) {
        QMember member = QMember.member;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(member.email.eq(email))
        .and(member.userId.eq(userId));
        return exists(builder);
    }

}

