package teamexpress.velo9.member.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import teamexpress.velo9.member.domain.Member;
import teamexpress.velo9.member.domain.MemberRepository;
import teamexpress.velo9.member.domain.Role;
import teamexpress.velo9.member.dto.MemberDTO;
import teamexpress.velo9.member.dto.MemberEditDTO;
import teamexpress.velo9.member.dto.PasswordDTO;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberServiceTest {

	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	void editMember() {
		//given
		Member member = memberRepository.save(createMember("nickname", "testUsername", "1234", "zxcv@nate.com", Role.ROLE_USER));

		//when
		MemberDTO memberDTO = memberService.editMember(member.getId(), new MemberEditDTO("nickname", "introduce", "blogTitle", "socialEmail", "socialGithub"));
		Member findMember = memberRepository.findById(member.getId()).orElse(null);

		//then
		assertThat(memberDTO).extracting("blogTitle").isEqualTo("blogTitle");
		assertThat(findMember).extracting("blogTitle").isEqualTo("blogTitle");

	}

	@Test
	void changPassword() {
		//given
		Member oldMember = memberRepository.save(createMember("nickname", "testUsername", "1234", "zxcv@nate.com", Role.ROLE_USER));

		PasswordDTO passwordDTO = new PasswordDTO();
		passwordDTO.setOldPassword("1234");
		passwordDTO.setNewPassword("1111");

		//when
		memberService.changePassword(oldMember.getId(), passwordDTO);
		Member changeMember = memberRepository.findByNickname("nickname").get();

		String oldPwd = oldMember.getPassword();
		String newPwd = changeMember.getPassword();

		//then
		assertThat(oldPwd).isNotEqualTo(newPwd);
	}



	private Member createMember(String nickname, String username, String password, String email, Role roleUser) {
		return Member.builder().nickname(nickname).blogTitle(nickname).username(username).password(passwordEncoder.encode(password)).email(email).posts(new ArrayList<>()).role(roleUser).build();
	}

}
