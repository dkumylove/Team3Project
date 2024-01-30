package org.team3.rank;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.team3.admin.option.Repository.OptionRankRepository;
import org.team3.admin.option.entities.OptionRank;
import org.team3.commons.Utils;

import java.util.List;

@Controller
@RequestMapping("/rank")
@RequiredArgsConstructor
public class RankController {

    private final Utils utils;
    private final OptionRankRepository optionRankRepository;

    @GetMapping
    public String index(Model model){


        List<OptionRank> optionList = optionRankRepository.findAll();
        model.addAttribute("optionList", optionList);
        return utils.tpl("rank/index");
    }
}
