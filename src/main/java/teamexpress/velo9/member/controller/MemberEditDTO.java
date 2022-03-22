package teamexpress.velo9.member.controller;

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

	public MemberEditDTO(String nickname, String introduce, String blogTitle, String socialEmail, String socialGithub) {
		this.nickname = nickname;
		this.introduce = introduce;
		this.blogTitle = blogTitle;
		this.socialEmail = socialEmail;
		this.socialGithub = socialGithub;
	}
}
