package teamexpress.velo9.member.service;

import java.io.File;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailMessage;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailHandler {

	private final MailSender sender;
	private final MailMessage message;
	private final MimeMessageHelper messageHelper;

	public MailHandler(JavaMailSender jSender) throws MessagingException {
		this.sender = jSender;
		message = (MailMessage) jSender.createMimeMessage();
		messageHelper = new MimeMessageHelper((MimeMessage) message, true, "UTF-8");
	}

	public void setFrom(String fromAddress) throws MessagingException {
		messageHelper.setFrom(fromAddress);
	}

	public void setTo(String email) throws MessagingException {
		messageHelper.setTo(email);
	}

	public void setSubject(String subject) throws MessagingException {
		messageHelper.setSubject(subject);
	}

	public void setText(String text, boolean useHtml) throws MessagingException {
		messageHelper.setText(text, useHtml);
	}

	public void setAttach(String displayFileName, String pathToAttachment) throws Exception {
		File file = new ClassPathResource(pathToAttachment).getFile();
		FileSystemResource fsr = new FileSystemResource(file);
		messageHelper.addAttachment(displayFileName, fsr);
	}

	public void setInline(String contentId, String pathToInline) throws Exception {
		File file = new ClassPathResource(pathToInline).getFile();
		FileSystemResource fsr = new FileSystemResource(file);
		messageHelper.addInline(contentId, fsr);
	}

	public void send() {
		sender.send((SimpleMailMessage) message);
	}
}
