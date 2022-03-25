package teamexpress.velo9.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import teamexpress.velo9.member.dto.FindInfoDTO;
import teamexpress.velo9.member.dto.MailContentDTO;
import teamexpress.velo9.member.dto.MailDTO;

@RequiredArgsConstructor
@Service
public class MailService {

	private final MailSender mailSender;

	@Async
	public void sendMail(String email, String number) {
		send(email, number);
	}

	@Async
	public void sendMailWithFiles(MailDTO mailDTO, String number) throws Exception {
		MailContentDTO mailContent = getMailContent(mailDTO.getEmail(), number);
		MailHandler mailHandler = setEmailContentWithFiles(mailContent);
		mailHandler.send();
	}

	@Async
	public void sendMailFindId(FindInfoDTO findInfoDTO, String findUsername) {
		MailContentDTO mailContent = getFindMailContent(findInfoDTO, findUsername);
		SimpleMailMessage message = setEmailContent(mailContent);
		mailSender.send(message);

	}

	private void send(String email, String number) {
		MailContentDTO mailContent = getMailContent(email, number);
		SimpleMailMessage message = setEmailContent(mailContent);
		mailSender.send(message);
	}

	private SimpleMailMessage setEmailContent(MailContentDTO emailContent) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(emailContent.getAddress());
		message.setSubject(emailContent.getTitle());
		message.setText(emailContent.getMessage());
		return message;
	}

	private MailHandler setEmailContentWithFiles(MailContentDTO mailContent) throws Exception {
		MailHandler mailHandler = new MailHandler((JavaMailSender) mailSender);
		mailHandler.setTo(mailContent.getAddress());
		mailHandler.setSubject(mailContent.getTitle());
		String htmlContent = "<p>" + mailContent.getMessage() + "<p><img src=경로>";
		mailHandler.setText(htmlContent, true);
		mailHandler.setAttach("파일네임", "파일경로");
		mailHandler.setInline("확장자 제외한 사진이름", "사진경로");
		return mailHandler;
	}

	private MailContentDTO getMailContent(String email, String number) {
		String title = "Velo9 이메일 인증번호입니다.";
		String message = "인증번호 : " + number;
		return new MailContentDTO(email, title, message);
	}

	private MailContentDTO getFindMailContent(FindInfoDTO mailDTO, String findUsername) {
		String email = mailDTO.getEmail();
		String title = "회원님의 아이디 입니다.";
		String message = "회원님의 아이디는 : " + findUsername + " 입니다,";
		return new MailContentDTO(email, title, message);
	}
}
