package teamexpress.velo9.member.service;

import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import teamexpress.velo9.member.dto.MailContent;
import teamexpress.velo9.member.dto.MailDTO;

@RequiredArgsConstructor
@Service
public class MailService {

	private final JavaMailSender mailSender;

	@Async
	public void sendMail(MailDTO mailDTO) {
		MailContent emailContent = getEmailContent(mailDTO);
		SimpleMailMessage message = setEmailContent(emailContent);
		mailSender.send(message);
	}

	@Async
	public void sendMailWithFiles(MailContent mailContent) throws Exception {
		setEmailContentWithFiles(mailContent);
	}

	private SimpleMailMessage setEmailContent(MailContent emailContent) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(emailContent.getAddress());
		message.setSubject(emailContent.getTitle());
		message.setText(emailContent.getMessage());
		return message;
	}

	private void setEmailContentWithFiles(MailContent mailContent) throws Exception {
		MailHandler mailHandler = new MailHandler(mailSender);
		mailHandler.setTo(mailContent.getAddress());
		mailHandler.setSubject(mailContent.getTitle());
		String htmlContent = "<p>" + mailContent.getMessage() + "<p><img src=경로>";
		mailHandler.setText(htmlContent, true);
		mailHandler.setAttach("파일네임", "파일경로");
		mailHandler.setInline("확장자 제외한 사진이름","사진경로");
		mailHandler.send();
	}

	public MailContent getEmailContent(MailDTO mailDTO) {
		String email = mailDTO.getEmail();
		String title = "블로그 이메일 인증번호입니다.";
		String number = getRandomNumber();
		String message = "인증번호 : " + number;
		return new MailContent(email, title, message);
	}

	private String getRandomNumber() {
		Random random = new Random();
		String randomNumber = "";
		for (int i = 0; i < 6; i++) {
			int number = random.nextInt(9) + 1;
			randomNumber += number;
		}
		return randomNumber;
	}
}
