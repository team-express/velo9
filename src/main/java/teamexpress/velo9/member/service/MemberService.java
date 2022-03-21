package teamexpress.velo9.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamexpress.velo9.member.domain.Member;
import teamexpress.velo9.member.domain.MemberRepository;
import teamexpress.velo9.member.domain.MemberThumbnail;
import teamexpress.velo9.member.dto.MailDTO;
import teamexpress.velo9.member.dto.MemberSignupDTO;
import teamexpress.velo9.member.dto.MemberThumbnailDTO;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public Long join(MemberSignupDTO signupDTO) {
		checkDuplicateMember(signupDTO);
		encodePassword(signupDTO);
		Member member = signupDTO.toMember();
		memberRepository.save(member);
		return member.getId();
	}


	public void findEmail(MailDTO mailDTO) {
		memberRepository.findByEmail(mailDTO.getEmail())
			.ifPresent(m -> {
				throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
			});
	}

	@Transactional
	public void upload(MemberThumbnailDTO memberThumbnailDTO, Long memberId) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new NullPointerException());

		MemberThumbnail memberThumbnail = member.getMemberThumbnail();

		if (memberThumbnail != null) {
			memberThumbnailDTO.setId(memberThumbnail.getId());
		}

		member.uploadThumbnail(memberThumbnailDTO.toMemberThumbnail());

		memberRepository.save(member);
	}

	private void encodePassword(MemberSignupDTO signupDTO) {
		signupDTO.setPassword(passwordEncoder.encode(signupDTO.getPassword()));
	}

	private void checkDuplicateMember(MemberSignupDTO signupDTO) {
		validateUsername(signupDTO.getUsername());
		validateNickname(signupDTO.getNickname());
	}

	private void validateUsername(String username) {
		memberRepository.findByUsername(username)
			.ifPresent(m -> {
				throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
			});
	}

	private void validateNickname(String nickname) {
		memberRepository.findByNickname(nickname)
			.ifPresent(m -> {
				throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
			});
	}
}
