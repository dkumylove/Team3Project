package org.team3.rank;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.team3.admin.option.Repository.OptionRankRepository;
import org.team3.admin.option.entities.OptionRank;
import org.team3.admin.option.service.OptionRankInfoService;
import org.team3.board.controllers.BoardDataSearch;
import org.team3.board.entities.BoardData;
import org.team3.commons.ListData;
import org.team3.commons.Utils;
import org.team3.member.service.MemberInfoService;

import java.util.List;

@Controller
@RequestMapping("/rank")
@RequiredArgsConstructor
public class RankController {

    private final Utils utils;
    private final OptionRankInfoService optionRankInfoService;
    private final MemberInfoService memberInfoService;

    @GetMapping
    public String index(Model model){

        List<OptionRank> optionList = optionRankInfoService.getOptionAll();
        int member = memberInfoService.allMember();
        model.addAttribute("member", member);
        model.addAttribute("optionList", optionList);
        return utils.tpl("rank/index");
    }



    @GetMapping("/{optionname}")
    public String list(@PathVariable("optionname") String optionname,
                       @ModelAttribute OptionRank OptionRank, Model model) {

        OptionRank data = optionRankInfoService.getOptionBoard(optionname);

        model.addAttribute("optionrank", data);

        return utils.tpl("rank/listpage");
    }
}
