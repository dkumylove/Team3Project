package org.team3.admin.option.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.team3.admin.option.Repository.OptionRankRepository;
import org.team3.admin.option.controllers.RequestRankOption;
import org.team3.admin.option.entities.OptionRank;
import org.team3.admin.option.entities.Options;

@Service
@RequiredArgsConstructor
public class OptionRankSaveService {

    private final OptionRankRepository optionRankRepository;

    @Transactional
    public void save(RequestRankOption rankOption){
        OptionRank optionRank = new OptionRank();

        optionRank.setOptionDetail(rankOption.getOptionDetail());
        optionRank.setActive(rankOption.isActive());
        optionRank.setOptionname(rankOption.getOptionname());
        optionRank.setRank(rankOption.getRank());
        optionRankRepository.saveAndFlush(optionRank);

    }
}

