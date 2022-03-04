package teamexpress.velo9.member.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
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

	@Transactional
	public void save(MemberSignupDTO memberSignupDTO) {
		Member joinMember = new Member();
		Optional<Member> findMember = validateDuplicateMember(memberSignupDTO.getUsername());
		if (findMember.isEmpty()) {
			joinMember = Member.builder()
				.username(memberSignupDTO.getUsername())
				.password(memberSignupDTO.getPassword())
				.nickname(memberSignupDTO.getNickname())
				.email(memberSignupDTO.getEmail())
				.build();
		}
		memberRepository.save(joinMember);
	}

	private Optional<Member> validateDuplicateMember(String username) {
		Optional<Member> findMember = memberRepository.findByUsername(username);
		if (findMember.isPresent()) {
			throw new IllegalStateException("이미 존재하는 아이디입니다.");
		}
		return findMember;
	}

	public void findEmail(MailDTO mailDTO) {
		memberRepository.findAll().stream()
			.filter(m -> m.getEmail().equals(mailDTO.getEmail()))
			.forEach(member -> {
				throw new IllegalStateException("이미 존재하는 이메일 입니다.");
			});
	}
}
