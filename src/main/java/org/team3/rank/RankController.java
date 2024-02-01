package org.team3.rank;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.team3.admin.option.Repository.OptionRankRepository;
import org.team3.admin.option.entities.OptionRank;
import org.team3.admin.option.service.OptionRankInfoService;
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
}
