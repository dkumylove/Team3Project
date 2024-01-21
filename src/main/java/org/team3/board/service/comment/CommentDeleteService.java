package org.team3.board.service.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.team3.board.entities.CommentData;
import org.team3.board.repositories.CommentDataRepository;

@Service
@RequiredArgsConstructor
public class CommentDeleteService {
    private final CommentDataRepository commentDataRepository;
    private final CommentInfoService commentInfoService;

    public Long delete(Long seq) {

        CommentData data = commentInfoService.get(seq);
        Long boardDataSeq = data.getBoardData().getSeq();

        commentDataRepository.delete(data);
        commentDataRepository.flush();

        return boardDataSeq;
    }
}
