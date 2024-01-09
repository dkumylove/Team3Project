package org.team3.file.service;


import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.team3.commons.Utils;
import org.team3.commons.exceptions.UnAuthorizedException;
import org.team3.file.entities.FileInfo;
import org.team3.file.entities.QFileInfo;
import org.team3.file.repositories.FileInfoRepository;
import org.team3.member.MemberUtil;
import org.team3.member.entities.Member;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileDeleteService {

    private final FileInfoService infoService;
    private final MemberUtil memberUtil;
    private final FileInfoRepository repository;

    public void delete(Long seq) {
        FileInfo data = infoService.get(seq);

        // 파일 삭제 권한 체크
        Member member = memberUtil.getMember();
        String createdBy = data.getCreatedBy();
        if (StringUtils.hasText(createdBy) && (!memberUtil.isLogin() || (!memberUtil.isAdmin() && StringUtils.hasText(createdBy) && !createdBy.equals(member.getUserId())))) {
            throw new UnAuthorizedException(Utils.getMessage("Not.your.file", "errors"));
        }

        File file = new File(data.getFilePath());
        if (file.exists()) file.delete();

        List<String> thumbsPath = data.getThumbsPath();
        if (thumbsPath != null) {
            for (String path : thumbsPath) {
                File thumbFile = new File(path);
                if (thumbFile.exists()) thumbFile.delete();
            }
        }

        repository.delete(data);
        repository.flush();
    }

    public void delete(String gid, String location) {
        QFileInfo fileInfo = QFileInfo.fileInfo;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(fileInfo.gid.eq(gid));
        if (StringUtils.hasText(location)) {
            builder.and(fileInfo.location.eq(location));
        }

        List<FileInfo> items = (List<FileInfo>) repository.findAll(builder);

        items.forEach(i -> delete(i.getSeq()));
    }

    public void delete (String gid) {
        delete(gid, null);
    }
}