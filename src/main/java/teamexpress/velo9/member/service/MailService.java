package teamexpress.velo9.member.service;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import teamexpress.velo9.member.dto.MailContentDTO;
import teamexpress.velo9.member.dto.MailDTO;

@RequiredArgsConstructor
@Service
public class MailService {

	public static final int BOUND = 10;
	private final MailSender mailSender;

	@Async
	public void sendMail(MailDTO mailDTO) {
		MailContentDTO mailContent = getMailContent(mailDTO);
		SimpleMailMessage message = setEmailContent(mailContent);
		mailSender.send(message);
	}

	@Async
	public void sendMailWithFiles(MailDTO mailDTO) throws Exception {
		MailContentDTO mailContent = getMailContent(mailDTO);
		MailHandler mailHandler = setEmailContentWithFiles(mailContent);
		mailHandler.send();
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
		mailHandler.setInline("확장자 제외한 사진이름","사진경로");
		return mailHandler;
	}

	public MailContentDTO getMailContent(MailDTO mailDTO) {
		String email = mailDTO.getEmail();
		String title = "블로그 이메일 인증번호입니다.";
		String number = getRandomNumber();
		String message = "인증번호 : " + number;
		return new MailContentDTO(email, title, message);
	}

	private String getRandomNumber() {
		Random random = new Random();
		return IntStream.range(0, 6)
			.map(i -> random.nextInt(BOUND))
			.mapToObj(String::valueOf)
			.collect(Collectors.joining());
	}
}
