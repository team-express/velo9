package teamexpress.velo9.member.controller;

import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import teamexpress.velo9.member.dto.MemberSignupDTO;
import teamexpress.velo9.member.security.oauth.SessionConst;
import teamexpress.velo9.member.service.MemberService;

@RequiredArgsConstructor
@RestController
public class MemberController {

	private final MemberService memberService;

	@GetMapping("/signup")
	public void addMember() {
	}

	@PostMapping("/signup")
	public void addMember(@Validated @RequestBody MemberSignupDTO memberSignupDTO) {
		memberService.join(memberSignupDTO);
	}

	@PostMapping("/member/setting")
	public ResponseEntity<MemberDTO> editMember(@RequestBody MemberEditDTO memberEditDTO, HttpSession session) {
		Long memberId = (Long) session.getAttribute(SessionConst.LOGIN_MEMBER);
		MemberDTO memberDTO = memberService.editMember(memberId, memberEditDTO);
		return new ResponseEntity<>(memberDTO, HttpStatus.OK);
	}
}
