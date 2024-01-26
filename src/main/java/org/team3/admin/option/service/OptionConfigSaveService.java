package org.team3.admin.option.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.team3.admin.option.Repository.OptionRepository;

import org.team3.admin.option.entities.Options;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OptionConfigSaveService {

//    public List<String> getCategories() {
//        List<String> categories = new ArrayList<>();
//
//        if (StringUtils.hasText(category)) {
//            categories = Arrays.stream(category.trim().split("\\n"))
//                    .map(s -> s.trim().replaceAll("\\r", ""))
//                    .toList();
//        }
//
//        return categories;
//    }

    public final OptionRepository optionRepository;
    public void save(String options){
//        Options option = new Options();

        String[] optionlist = options.split("\\n");

        for(String option : optionlist){
            String[] parts = option.split("_");

            if (parts.length == 2) {
                String optionname = parts[0];
                String category = parts[1];

                // 필요에 따라 특수문자나 공백을 제거하는 등의 로직을 추가할 수 있습니다.
//                category = category.replaceAll("\\r", "");
//                optionName = optionName.replaceAll("\\r", "");

                // 옵션 객체를 생성하고 설정합니다.
                Options options1 = new Options();
                options1.setCategory(category);
                options1.setOptionname(optionname);

                // 생성된 옵션 객체를 저장합니다.
                optionRepository.saveAndFlush(options1);
            } else {
                // 앞뒤로 "_" 기호가 정확히 하나만 있는 경우가 아니라면 처리하지 않습니다.
                // 예를 들어, "category_option"과 같이 "_"이 여러 개 있는 경우는 무시합니다.
                System.out.println("Invalid format: " + option);
            }
        }
    }
}
