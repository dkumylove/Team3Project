package org.team3.file.controllers;

import lombok.RequiredArgsConstructor;
import org.team3.commons.ExceptionProcessor;
import org.team3.commons.exceptions.AlertBackException;
import org.team3.commons.exceptions.CommonException;
import org.team3.file.service.FileDeleteService;
import org.team3.file.service.FileDownloadService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 일반 컨트롤러
 */
@Controller
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController implements ExceptionProcessor {

    private final FileDeleteService deleteService;
    private final FileDownloadService downloadService;

//    @GetMapping("/upload")
//    public String upload() {
//
//        return "upload";
//    }

    @GetMapping("/delete/{seq}")
    public String delete(@PathVariable("seq") Long seq, Model model) {

        try {
            deleteService.delete(seq);
            /* callbackFileDelete가 함수이면 실행하라는 의미 -> 삭제가 되면 이 함수가 실행함 */
            String script = String.format("if (typeof parent.callbackFileDelete=='function') parent.callbackFileDelete(%d);", seq);
            model.addAttribute("script", script);
        } catch (CommonException e) {
            e.printStackTrace();
            throw new AlertBackException(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return "common/_execute_script";
    }

    /*
    @RequestMapping("/download/{seq}")
    public void download(@PathVariable("seq") Long seq, HttpServletResponse response) throws IOException {
        response.setHeader("Content-Disposition", "attachment; filename=test.txt");

        PrintWriter out = response.getWriter();
        out.println("test1");
        out.println("test2");
    }
     */
    @ResponseBody
    @RequestMapping("/download/{seq}")
    public void download(@PathVariable("seq") Long seq) {
        try {
            downloadService.download(seq);
        } catch (CommonException e) {
            throw new AlertBackException(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}