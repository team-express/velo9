package teamexpress.velo9.member.service;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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

	public static final int BOUND = 10;
	private final JavaMailSender mailSender;

	@Async
	public void sendMail(MailDTO mailDTO) {
		MailContent emailContent = getEmailContent(mailDTO);
		SimpleMailMessage message = setEmailContent(emailContent);
		mailSender.send(message);
	}

	@Async
	public void sendMailWithFiles(MailDTO mailDTO) throws Exception {
		MailContent mailContent = getEmailContent(mailDTO);
		MailHandler mailHandler = setEmailContentWithFiles(mailContent);
		mailHandler.send();
	}

	private SimpleMailMessage setEmailContent(MailContent emailContent) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(emailContent.getAddress());
		message.setSubject(emailContent.getTitle());
		message.setText(emailContent.getMessage());
		return message;
	}

	private MailHandler setEmailContentWithFiles(MailContent mailContent) throws Exception {
		MailHandler mailHandler = new MailHandler(mailSender);
		mailHandler.setTo(mailContent.getAddress());
		mailHandler.setSubject(mailContent.getTitle());
		String htmlContent = "<p>" + mailContent.getMessage() + "<p><img src=경로>";
		mailHandler.setText(htmlContent, true);
		mailHandler.setAttach("파일네임", "파일경로");
		mailHandler.setInline("확장자 제외한 사진이름","사진경로");
		return mailHandler;
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
		return IntStream.range(0, 6)
			.map(i -> random.nextInt(BOUND))
			.mapToObj(String::valueOf)
			.collect(Collectors.joining());
	}
}
