package org.team3.board.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.team3.board.service.SaveBoardDataService;
import org.team3.commons.rests.JSONData;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class ApiBoardController {

    private final SaveBoardDataService saveBoardDataService;

    @GetMapping("/save_post/{bSeq}")
    public JSONData<Object> savePost(@PathVariable("bSeq") Long bSeq) {
        saveBoardDataService.save(bSeq);

        return new JSONData<>();
    }

    @DeleteMapping("/save_post/{bSeq}")
    public JSONData<Object> deleteSavePost(@PathVariable("bSeq") Long bSeq) {
        saveBoardDataService.delete(bSeq);

        return new JSONData<>();
    }
}