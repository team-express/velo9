package teamexpress.velo9.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import teamexpress.velo9.member.dto.MailDTO;
import teamexpress.velo9.member.service.MailService;
import teamexpress.velo9.member.service.MemberService;

@RequiredArgsConstructor
@RestController
public class MailController {

	private final MailService mailService;
	private final MemberService memberService;

	@PostMapping("/sendMail")
	private String sendMail(@Validated @RequestBody MailDTO mailDTO) {
		memberService.findEmail(mailDTO);
		mailService.sendMail(mailDTO);
		return "ok";
	}
}
