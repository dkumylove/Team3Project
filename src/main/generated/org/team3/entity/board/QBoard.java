package org.team3.entity.board;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoard extends EntityPathBase<Board> {

    private static final long serialVersionUID = 1805654901L;

    public static final QBoard board = new QBoard("board");

    public final org.team3.entity.abstractCommon.QBaseMember _super = new org.team3.entity.abstractCommon.QBaseMember(this);

    public final BooleanPath active = createBoolean("active");

    public final StringPath bId = createString("bId");

    public final StringPath bName = createString("bName");

    public final NumberPath<Integer> bRowsPerPage = createNumber("bRowsPerPage", Integer.class);

    public final StringPath category = createString("category");

    public final EnumPath<BoardAuthority> commentAccessType = createEnum("commentAccessType", BoardAuthority.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final EnumPath<BoardAuthority> listAccessType = createEnum("listAccessType", BoardAuthority.class);

    public final BooleanPath locationAfterWriting = createBoolean("locationAfterWriting");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    public final NumberPath<Integer> pageCountPc = createNumber("pageCountPc", Integer.class);

    public final EnumPath<BoardAuthority> replyAccessType = createEnum("replyAccessType", BoardAuthority.class);

    public final EnumPath<SkinType> skin = createEnum("skin", SkinType.class);

    public final BooleanPath useComment = createBoolean("useComment");

    public final BooleanPath useEditor = createBoolean("useEditor");

    public final BooleanPath useReply = createBoolean("useReply");

    public final BooleanPath useUploadFile = createBoolean("useUploadFile");

    public final BooleanPath useUploadImage = createBoolean("useUploadImage");

    public final EnumPath<BoardAuthority> viewAccessType = createEnum("viewAccessType", BoardAuthority.class);

    public final EnumPath<BoardAuthority> writeAccessType = createEnum("writeAccessType", BoardAuthority.class);

    public QBoard(String variable) {
        super(Board.class, forVariable(variable));
    }

    public QBoard(Path<? extends Board> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoard(PathMetadata metadata) {
        super(Board.class, metadata);
    }

}

