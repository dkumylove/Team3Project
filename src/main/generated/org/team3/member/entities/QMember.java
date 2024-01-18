package org.team3.member.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -2070350731L;

    public static final QMember member = new QMember("member1");

    public final org.team3.commons.entities.QBase _super = new org.team3.commons.entities.QBase(this);

    public final ListPath<Authorities, QAuthorities> authorities = this.<Authorities, QAuthorities>createList("authorities", Authorities.class, QAuthorities.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath deleteMember = createString("deleteMember");

    public final StringPath email = createString("email");

    public final StringPath gid = createString("gid");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public final StringPath nickName = createString("nickName");

    public final StringPath password = createString("password");

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final StringPath updateMemberMail = createString("updateMemberMail");

    public final StringPath updateMemberNickname = createString("updateMemberNickname");

    public final StringPath updateMemberPassword = createString("updateMemberPassword");

    public final StringPath userId = createString("userId");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

