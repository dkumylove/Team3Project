package org.team3.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.team3.file.service.FileUploadService;
import org.team3.member.Authority;
import org.team3.member.controllers.JoinValidator;
import org.team3.member.controllers.RequestJoin;
import org.team3.member.entities.Authorities;
import org.team3.member.entities.Member;
import org.team3.member.entities.Profile;
import org.team3.member.repositories.AuthoritiesRepository;
import org.team3.member.repositories.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class JoinService {

    private final MemberRepository memberRepository;
    private final AuthoritiesRepository authoritiesRepository;
    private final JoinValidator validator;
    private final PasswordEncoder encoder;
    private final FileUploadService uploadService;

    public void process(RequestJoin form, Errors errors) {

        validator.validate(form, errors);

        if (errors.hasErrors()) {
            return;
        }

        // 비밀번호 BCrypt로 해시화
        String hash = encoder.encode(form.getPassword());

//        Member member = new Member();
//        member.setEmail(form.getEmail());
        //Member member = new ModelMapper().map(form, Member.class);
        Member member = new Member();
        member.setEmail(form.getEmail());
        member.setUserId(form.getUserId());
        member.setName(form.getName());
        member.setPassword(hash);
        member.setGid(form.getGid());

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
