package teamexpress.velo9.member.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamexpress.velo9.member.domain.Member;
import teamexpress.velo9.member.domain.MemberRepository;
import teamexpress.velo9.member.dto.MailDTO;
import teamexpress.velo9.member.dto.MemberSignupDTO;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public void join(MemberSignupDTO signupDTO) {
		checkDuplicateMember(signupDTO);
		Member member = Member.createMember(signupDTO.getUsername(), passwordEncoder.encode(signupDTO.getPassword()),
											signupDTO.getNickname(), signupDTO.getEmail());
		memberRepository.save(member);
	}

	private void checkDuplicateMember(MemberSignupDTO signupDTO) {
		validateUsername(signupDTO.getUsername());
		validateNickname(signupDTO.getNickname());
	}

	private void validateUsername(String username) {
		Optional<Member> findMember = memberRepository.findByUsername(username);
		if (findMember.isPresent()) {
			throw new IllegalStateException("이미 존재하는 아이디입니다.");
		}
	}

	private void validateNickname(String nickname) {
		Optional<Member> findMember = memberRepository.findByNickname(nickname);
		if (findMember.isPresent()) {
			throw new IllegalStateException("이미 존재하는 닉네임입니다.");
		}
	}

	public void findEmail(MailDTO mailDTO) {
		memberRepository.findAll().stream()
			.filter(m -> m.getEmail().equals(mailDTO.getEmail()))
			.forEach(member -> {
				throw new IllegalStateException("이미 존재하는 이메일 입니다.");
			});
	}
}
