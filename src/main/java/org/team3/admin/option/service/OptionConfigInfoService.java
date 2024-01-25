package org.team3.admin.option.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.team3.admin.option.Repository.OptionRepository;
import org.team3.admin.option.entities.Options;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OptionConfigInfoService {

    private final OptionRepository optionRepository;


    /**
     * 옵션 네임으로 불러오기
     * @param optionname
     * @return
     */
    public Options getOption(String optionname){
        Options options = optionRepository.findById(optionname).orElse(null);
        return options;
    }


    /**
     * 옵션 이름만 가져오는 list
     * @return
     */
    public List<String> getOptionname(){
        List<String> selectOptionname = new ArrayList<>();
        List<Options> all = optionRepository.findAll();
        if(!all.isEmpty() && all.size()>0){
            for(int i = 0; i<all.size(); i++) {
                selectOptionname.add(all.get(i).getOptionname());
            }
        }
        return selectOptionname;
    }

    /**
     * 전체 리스트 불러오기
     * @return
     */
    public List<Options> getOptionList(){
        List<Options> all = optionRepository.findAll();
        return all;
    }
}
