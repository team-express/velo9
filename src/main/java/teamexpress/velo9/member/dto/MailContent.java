package teamexpress.velo9.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MailContent {

	private String address;
	private String title;
	private String message;
}
