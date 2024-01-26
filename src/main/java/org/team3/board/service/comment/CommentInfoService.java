package org.team3.board.service.comment;

import com.querydsl.core.BooleanBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.team3.board.controllers.BoardDataSearch;
import org.team3.board.controllers.comment.RequestComment;
import org.team3.board.entities.BoardData;
import org.team3.board.entities.CommentData;
import org.team3.board.entities.QCommentData;
import org.team3.board.repositories.BoardDataRepository;
import org.team3.board.repositories.CommentDataRepository;
import org.team3.member.MemberUtil;
import org.team3.member.entities.Member;

import java.util.List;

import static org.springframework.data.domain.Sort.Order.asc;
import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
public class CommentInfoService {

    private final CommentDataRepository commentDataRepository;
    private final BoardDataRepository boardDataRepository;
    private final MemberUtil memberUtil;
    private final HttpServletRequest request;

    /**
     * 댓글 단일 조회
     *
     * @param seq : 댓글 번호
     * @return
     */
    public CommentData get(Long seq) {
        CommentData data = commentDataRepository.findById(seq).orElseThrow(CommentNotFoundException::new);

        addCommentInfo(data);

        return data;
    }

    public RequestComment getForm(Long seq) {
        CommentData data = get(seq);
        RequestComment form = new ModelMapper().map(data, RequestComment.class);

        form.setBoardDataSeq(data.getBoardData().getSeq());

        return form;
    }

    /**
     * 게시글별 댓글 목록 조회
     *
     * @param boardDataSeq
     * @return
     */
    public List<CommentData> getList(Long boardDataSeq) {
        QCommentData commentData = QCommentData.commentData;
        BooleanBuilder andBuilder = new BooleanBuilder();
        andBuilder.and(commentData.boardData.seq.eq(boardDataSeq));

        List<CommentData> items = (List<CommentData>)commentDataRepository.findAll(andBuilder, Sort.by(desc("listOrder"), asc("createdAt")));

        items.forEach(this::addCommentInfo);

        return items;
    }


//    /**
//     * 사용자별 댓글 목록 조회
//     * 1/26 이지은
//     * @param userId
//     * @return
//     */
//    public List<CommentData> getUserComments(String userId) {
//
//        QCommentData commentData = QCommentData.commentData;
//        BooleanBuilder andBuilder = new BooleanBuilder();
//
//        // 유저 아이디로 댓글 필터링
//        andBuilder.and(commentData.member.userId.eq(userId));
//
//        List<CommentData> items = (List<CommentData>) commentDataRepository.findAll(andBuilder, Sort.by(desc("createdAt")));
//
//        // 각 댓글에 대한 추가 정보 처리
//        items.forEach(this::addCommentInfo);
//
//        return items;
//    }


    /**
     * 댓글 추가 정보 처리
     *
     * @param data
     */
    private void addCommentInfo(CommentData data) {
        boolean editable = false, deletable = false, mine = false;

        Member _member = data.getMember(); // 댓글을 작성한 회원

        /*
         * 1) 관리자는 댓글 수정, 삭제 제한 없음
         *
         */
        if (memberUtil.isAdmin()) {
            editable = deletable = true;
        }

        /**
         * 회원이 작성한 댓글이면 현재 로그인 사용자의 아이디와 동일해야 수정, 삭제 가능
         *
         */
        if (_member != null && memberUtil.isLogin()
                && _member.getUserId().equals(memberUtil.getMember().getUserId())) {
            editable = deletable = mine = true;
        }

        // 비회원 -> 비회원 비밀번호가 확인 된 경우 삭제, 수정 가능
        // 비회원 비밀번호 인증 여부 세션에 있는 guest_confirmed_게시글번호 true -> 인증
        HttpSession session = request.getSession();
        String key = "guest_comment_confirmed_" + data.getSeq();
        Boolean guestConfirmed = (Boolean)session.getAttribute(key);
        if (_member == null && guestConfirmed != null && guestConfirmed) {
            editable = true;
            deletable = true;
            mine = true;
        }

        // 수정 버튼 노출 여부
        // 관리자 - 노출, 회원 게시글 - 직접 작성한 게시글, 비회원
        boolean showEditButton = memberUtil.isAdmin() || mine || _member == null;
        boolean showDeleteButton = showEditButton;

        data.setShowEditButton(showEditButton);
        data.setShowDeleteButton(showDeleteButton);

        data.setEditable(editable);
        data.setDeletable(deletable);
        data.setMine(mine);
    }

    /**
     * 게시글별 댓글 수 업데이트
     *
     * @param boardDataSeq : 게시글 번호
     */
    public void updateCommentCount(Long boardDataSeq) {
        BoardData data = boardDataRepository.findById(boardDataSeq).orElse(null);
        if (data == null) {
            return;
        }

        int total = commentDataRepository.getTotal(boardDataSeq);

        data.setCommentCount(total);

        boardDataRepository.flush();

    }
}
