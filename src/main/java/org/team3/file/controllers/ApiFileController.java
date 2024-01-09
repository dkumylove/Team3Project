package org.team3.file.controllers;


import lombok.RequiredArgsConstructor;
import org.team3.commons.ExceptionRestProcessor;
import org.team3.commons.rests.JSONData;
import org.team3.file.entities.FileInfo;
import org.team3.file.service.FileDeleteService;
import org.team3.file.service.FileUploadService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class ApiFileController implements ExceptionRestProcessor {

    private final FileUploadService uploadService;
    private final FileDeleteService deleteService;

    @PostMapping
    public JSONData<List<FileInfo>> upload(@RequestParam("file") MultipartFile[] files,
                                           @RequestParam(name="gid", required = false) String gid,
                                           @RequestParam(name="location", required = false) String location,
                                           @RequestParam(name="imageOnly", required=false) boolean imageOnly,
                                           @RequestParam(name="singleFile", required = false) boolean singleFile) {

        List<FileInfo> uploadedFiles = uploadService.upload(files, gid, location, imageOnly, singleFile);

        return new JSONData<>(uploadedFiles);
    }

    @GetMapping("/{seq}")
    public void delete(@PathVariable("seq") Long seq) {

        deleteService.delete(seq);
    }
}