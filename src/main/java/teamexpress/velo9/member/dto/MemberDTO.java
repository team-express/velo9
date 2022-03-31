package teamexpress.velo9.member.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import teamexpress.velo9.member.domain.Member;

@Data
public class MemberDTO {

	private String username;
	private String nickname;
	@JsonIgnore
	private String password;
	private String email;
	private String introduce;
	private String blogTitle;
	private String socialEmail;
	private String socialGithub;

	public MemberDTO(Member member) {
		this.username = member.getUsername();
		this.nickname = member.getNickname();
		this.password = member.getPassword();
		this.email = member.getEmail();
		this.introduce = member.getIntroduce();
		this.blogTitle = member.getBlogTitle();
		this.socialEmail = member.getSocialEmail();
		this.socialGithub = member.getSocialGithub();
	}
}
