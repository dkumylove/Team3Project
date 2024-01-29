package org.team3;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.team3.member.Authority;
import org.team3.member.entities.Authorities;
import org.team3.member.entities.Member;
import org.team3.member.repositories.AuthoritiesRepository;
import org.team3.member.repositories.MemberRepository;

@SpringBootTest
class ProjectApplicationTests {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private AuthoritiesRepository authoritiesRepository;

	@Test @Disabled
	void contextLoads() {
		Member member = memberRepository.findByUserId("user01").orElse(null);

		Authorities authorities = new Authorities();
		authorities.setMember(member);
		authorities.setAuthority(Authority.ADMIN);

		authoritiesRepository.saveAndFlush(authorities);
	}

}
