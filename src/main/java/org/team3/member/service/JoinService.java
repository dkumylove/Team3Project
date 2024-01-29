package org.team3.member.service;

import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.team3.admin.option.service.OptionConfigInfoService;
import org.team3.file.service.FileUploadService;
import org.team3.member.Authority;
import org.team3.member.controllers.JoinValidator;
import org.team3.member.controllers.RequestJoin;
import org.team3.member.entities.Authorities;
import org.team3.member.entities.Member;
import org.team3.member.entities.Profile;
import org.team3.member.repositories.AuthoritiesRepository;
import org.team3.member.repositories.MemberRepository;
import org.team3.admin.option.entities.Options;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class JoinService {

    private final MemberRepository memberRepository;
    private final AuthoritiesRepository authoritiesRepository;
    private final JoinValidator validator;
    private final PasswordEncoder encoder;
    private final FileUploadService uploadService;
    private final OptionConfigInfoService optionConfigInfoService;

    public void process(RequestJoin form, Errors errors) {

        validator.validate(form, errors);

        if (errors.hasErrors()) {
            return;
        }

        // 비밀번호 BCrypt로 해시화
        String hash = encoder.encode(form.getPassword());

//        Member member = new Member();
//        member.setEmail(form.getEmail());
        Member member = new ModelMapper().map(form, Member.class);
        String password = encoder.encode(form.getPassword());
        member.setPassword(password);
        List<Options> optionsList = new ArrayList<>();

        Set<String> options = form.getOption();

        for (String option : options) {
            Options option1 = optionConfigInfoService.getOption(option);
            optionsList.add(option1);
        }

        member.setOption(optionsList);


//        System.out.println("option.length" + option.length);

//        for(int i=0; i<option.length; i++){
//            Options option1 = optionConfigInfoService.getOption(option[i]);
//            System.out.println("option1" + option1.getOptionname());
//            if(option1!=null) {
//                optionsList.add(option1);
//            }
//        }

//        member.setOption(optionsList);

        /*
        Member member = new Member();
        member.setEmail(form.getEmail());
        member.setUserId(form.getUserId());
        member.setName(form.getName());
        member.setPassword(hash);
        member.setGid(form.getGid());

        */
        process(member);

        // 회원가입시에는 일반 사용자 권한(USER) 부여
        Authorities authorities = new Authorities();
        authorities.setMember(member);
        authorities.setAuthority(Authority.USER);
        authoritiesRepository.saveAndFlush(authorities);


        // 파일 업로드 완료처리
        uploadService.processDone(form.getGid());

    }

    /**
     * DB에 저장
     * @param member
     */
    public void process(Member member) {
        memberRepository.saveAndFlush(member);
    }

    public void updateProfile(Member member, Profile profile) {
        member.setName(profile.getName());
        member.setUserId(profile.getUserId());
        member.setPassword(profile.getPassword());
        member.setNickName(profile.getNickName());
        member.setEmail(profile.getEmail());
    }
}
