package teamexpress.velo9.post.dto;

import lombok.Data;
import teamexpress.velo9.member.domain.Member;

@Data
public class PostMemberDTO {

	private String memberName;
	private String memberIntroduce;
	private String socialGithub;
	private String socialEmail;
	private PostMemberThumbnailDTO memberThumbnailDTO;

	public PostMemberDTO(Member member) {
		this.memberName = member.getNickname();
		this.memberIntroduce = member.getIntroduce();
		this.socialGithub = member.getSocialGithub();
		this.socialEmail = member.getSocialEmail();
		this.memberThumbnailDTO = new PostMemberThumbnailDTO(member);
	}
}
