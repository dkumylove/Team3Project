package org.team3.admin.option.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.team3.admin.option.Repository.OptionRankRepository;
import org.team3.admin.option.entities.OptionRank;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OptionRankInfoService {

    private final OptionRankRepository optionRankRepository;

    public List<OptionRank> getOptionAll(){
        return optionRankRepository.findAll();
    }
}
