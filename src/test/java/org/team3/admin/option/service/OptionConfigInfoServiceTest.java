package org.team3.admin.option.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.team3.admin.option.Repository.OptionRepository;
import org.team3.admin.option.entities.Options;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OptionConfigInfoServiceTest {

    @Autowired
    private OptionRepository optionRepository;
    @Autowired
    private OptionConfigInfoService configInfoService;
    @Test
    public void Test(){
        List<Options> all = optionRepository.findAll();
        all.stream().forEach(System.out::println);
    }


    @Test
    public void Test1(){
        List<String> optionname = configInfoService.getOptionname();
        optionname.stream().forEach(System.out::println);
        System.out.println(optionname.size());
    }

    @Test
    public void Test2(){
        Options eom = configInfoService.getOption("EOM");
        System.out.println(eom.getOptionname());
        System.out.println(eom.getCategory());
    }
}