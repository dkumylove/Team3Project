package org.team3.admin.option.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.team3.admin.option.Repository.OptionRankRepository;
import org.team3.admin.option.entities.OptionRank;
import org.team3.admin.option.entities.Options;

@Service
@RequiredArgsConstructor
public class OptionRankSaveService {

    private final OptionRankRepository optionRankRepository;

    @Transactional
    public void save(String details, String optionname,  boolean active){
        OptionRank optionRank = new OptionRank();

        optionRank.setOptionDetail(details);
        optionRank.setActive(active);
        optionRank.setOptionname(optionname);
        optionRankRepository.saveAndFlush(optionRank);

    }
}

