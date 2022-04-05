package teamexpress.velo9.post.dto;

import lombok.Data;
import teamexpress.velo9.member.domain.Member;

@Data
public class PostMemberDTO {

	private String name;
	private String introduce;
	private String socialGithub;
	private String socialEmail;
	private PostMemberThumbnailDTO thumbnail;

	public PostMemberDTO(Member member) {
		this.name = member.getNickname();
		this.introduce = member.getIntroduce();
		this.socialGithub = member.getSocialGithub();
		this.socialEmail = member.getSocialEmail();
		this.thumbnail = new PostMemberThumbnailDTO(member);
	}
}
