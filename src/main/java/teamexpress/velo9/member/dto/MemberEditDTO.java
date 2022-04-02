package teamexpress.velo9.member.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberEditDTO {

	private String nickname;
	private String introduce;
	private String blogTitle;
	private String socialEmail;
	private String socialGithub;
}
