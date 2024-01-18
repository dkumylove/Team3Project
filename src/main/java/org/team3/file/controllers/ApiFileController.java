package org.team3.file.controllers;

import lombok.RequiredArgsConstructor;
import org.team3.commons.ExceptionRestProcessor;
import org.team3.commons.rests.JSONData;
import org.team3.file.entities.FileInfo;
import org.team3.file.service.FileDeleteService;
import org.team3.file.service.FileInfoService;
import org.team3.file.service.FileUploadService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class ApiFileController implements ExceptionRestProcessor {
    private final FileDeleteService deleteService;
    private final FileUploadService uploadService;

    /**
     * @param files
     * @param gid
     * @param location
     * @param imageOnly -> 이미지만 올릴 수 있도록 통제 (어떤 경우는 엑셀파일만 올릴 수 있도록 할 수 있음)
     * @param singleFile
     * @param done -> 이미지 수정후 바로 적용 여부(false : 기존이미지, true : 변경된 이미지 적용
     * @return
     */
    @PostMapping
    public JSONData<List<FileInfo>> upload(@RequestParam("file")MultipartFile[] files,
                                           @RequestParam(name = "gid", required = false) String gid,
                                           @RequestParam(name = "location", required = false) String location,
                                           @RequestParam(name = "imageOnly", required = false) boolean imageOnly,
                                           @RequestParam(name = "singleFile", required = false) boolean singleFile,
                                           @RequestParam(name = "done", required = false) boolean done){

        // 커맨드 객체로 이용하는 것도 하나의 방법... singleFile
        List<FileInfo> uploadedfiles = uploadService.upload(files, gid, location, imageOnly, singleFile);

//        JSONData<List<FileInfo>> data = new JSONData<>();
//        data.setData(fileInfos);
//        return data;

        if (done) {
           uploadService.processDone(gid);
        }
        return new JSONData<>(uploadedfiles);
    }

    /** 자바 스크립트에서 사용
     @DeleteMapping
     */
    @GetMapping("/{seq}")
    public void delete(@PathVariable("seq") Long seq){
        deleteService.delete(seq);
    }
}
