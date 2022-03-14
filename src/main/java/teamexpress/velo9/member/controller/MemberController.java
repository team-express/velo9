package teamexpress.velo9.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import teamexpress.velo9.member.dto.MemberSignupDTO;
import teamexpress.velo9.member.service.MemberService;

@RequiredArgsConstructor
@RestController
public class MemberController {

	private final MemberService memberService;

	@GetMapping("/signup")
	public void addMember() {
	}

	@PostMapping("/signup")
	public String addMember(@Validated @RequestBody MemberSignupDTO memberSignupDTO) {
		memberService.join(memberSignupDTO);
		return "ok";
	}
}
