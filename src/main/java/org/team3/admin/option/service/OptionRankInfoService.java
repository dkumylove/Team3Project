package org.team3.admin.option.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.team3.admin.option.Repository.OptionRankRepository;
import org.team3.admin.option.entities.OptionRank;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OptionRankInfoService {

    private final OptionRankRepository optionRankRepository;

    public List<OptionRank> getOptionAll(){
        return optionRankRepository.findAll();
    }

    public OptionRank getOptionBoard(String oprionname){
        OptionRank optionRank = optionRankRepository.findById(oprionname).orElse(null);
        return optionRank;
    }
}
