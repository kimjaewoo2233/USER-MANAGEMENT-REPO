package com.example.springsecurity.domain.entity;

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

    private static final long serialVersionUID = 147384075L;

    public static final QMember member = new QMember("member1");

    public final EnumPath<com.example.springsecurity.domain.Gender> gender = createEnum("gender", com.example.springsecurity.domain.Gender.class);

    public final SetPath<Role, QRole> memberRole = this.<Role, QRole>createSet("memberRole", Role.class, QRole.class, PathInits.DIRECT2);

    public final StringPath nickName = createString("nickName");

    public final StringPath password = createString("password");

    public final StringPath username = createString("username");

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

