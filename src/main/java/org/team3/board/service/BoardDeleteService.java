package org.team3.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.team3.board.entities.BoardData;
import org.team3.board.entities.CommentData;
import org.team3.board.repositories.BoardDataRepository;
import org.team3.board.repositories.CommentDataRepository;
import org.team3.file.service.FileDeleteService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardDeleteService {
    private final BoardDataRepository boardDataRepository;
    private final CommentDataRepository commentDataRepository;
    private final BoardInfoService boardInfoService;
    private final FileDeleteService fileDeleteService;
    private final BoardAuthService boardAuthService;

    /**
     * 게시글 삭제
     *
     * @param seq
     */
    public void delete(Long seq) {

        // 삭제 권한 체크
        boardAuthService.check("delete", seq);

        BoardData data = boardInfoService.get(seq);

        List<CommentData> comments = data.getComments();
        if (comments != null && comments.isEmpty()) {
            commentDataRepository.deleteAll(comments);
            commentDataRepository.flush();
        }

        String gid = data.getGid();

        boardDataRepository.delete(data);
        boardDataRepository.flush();

        // 업로드된 파일 삭제
        fileDeleteService.delete(gid);
    }
}
