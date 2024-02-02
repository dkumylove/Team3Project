package org.team3.rank;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.team3.admin.option.Repository.OptionRankRepository;
import org.team3.admin.option.entities.OptionRank;
import org.team3.admin.option.entities.Options;
import org.team3.admin.option.service.OptionConfigInfoService;
import org.team3.admin.option.service.OptionRankInfoService;
import org.team3.board.controllers.BoardDataSearch;
import org.team3.board.controllers.comment.RequestComment;
import org.team3.board.entities.BoardData;
import org.team3.commons.ListData;
import org.team3.commons.Utils;
import org.team3.member.MemberUtil;
import org.team3.member.service.MemberInfoService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/rank")
@RequiredArgsConstructor
public class RankController {

    private final Utils utils;
    private final OptionRankInfoService optionRankInfoService;
    private final MemberInfoService memberInfoService;
    private final OptionConfigInfoService optionConfigInfoService;
    private final MemberUtil memberUtil;


    @GetMapping
    public String index(Model model){

        List<Options> optionList = optionConfigInfoService.getOptionList();
        int member = memberInfoService.allMember();
        model.addAttribute("member", member);
        model.addAttribute("optionList", optionList);
        return utils.tpl("rank/index");
    }



    @GetMapping("/{optionname}")
    public String list(@PathVariable("optionname") String optionname,
                       @ModelAttribute OptionRank OptionRank, Model model) {

        Options data = optionConfigInfoService.getOption(optionname);
        model.addAttribute("option", data);

        // 댓글 커맨드 객체 처리 S
        RequestComment requestComment = new RequestComment();
        if (memberUtil.isLogin()) {
            requestComment.setCommenter(memberUtil.getMember().getName());
        }

        model.addAttribute("requestComment", requestComment);
        // 댓글 커맨드 객체 처리 E

        return utils.tpl("rank/listpage");
    }



    @ModelAttribute
    private void commonProcess(Model model){

        List<String> addScript = new ArrayList<>(); // 프론트 자바스크립트
        List<String> addCss = new ArrayList<>(); // cdd추가

        addScript.add("rank/rank");
        addCss.add("rank/style");
        model.addAttribute("addScript", addScript);
        model.addAttribute("addCss", addCss);
    }

}
