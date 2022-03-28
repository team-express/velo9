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
import teamexpress.velo9.member.dto.MemberDTO;
import teamexpress.velo9.member.dto.MemberEditDTO;
import teamexpress.velo9.member.dto.MemberSignupDTO;
import teamexpress.velo9.member.dto.PasswordDTO;
import teamexpress.velo9.member.dto.SocialSignupDTO;
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

	@PostMapping("/changePassword")
	public void changePassword(@RequestBody PasswordDTO passwordDTO, HttpSession session) {
		Long memberId = (Long) session.getAttribute(SessionConst.LOGIN_MEMBER);
		memberService.changePassword(memberId, passwordDTO);
	}

	@PostMapping("/withdraw")
	public void withdrawMember(@RequestBody PasswordDTO passwordDTO, HttpSession session) {
		Long memberId = (Long) session.getAttribute(SessionConst.LOGIN_MEMBER);
		memberService.withdraw(memberId, passwordDTO);
	}

	@PostMapping("/socialSignup")
	public void socialSignup(@Validated @RequestBody SocialSignupDTO socialSignupDTO, HttpSession session) {
		Long memberId = (Long) session.getAttribute(SessionConst.LOGIN_MEMBER);
		memberService.joinSocial(socialSignupDTO, memberId);
	}
}
