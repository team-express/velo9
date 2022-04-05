package teamexpress.velo9.post.dto;

import lombok.Data;
import teamexpress.velo9.member.dto.MemberMainDTO;
import teamexpress.velo9.post.domain.Post;

@Data
public class PostMainDTO {

	private Long id;
	private String title;
	private String introduce;
	private int loveCount;
	private MemberMainDTO member;
	private PostThumbnailDTO postThumbnail;

	public PostMainDTO(Post post) {
		id = post.getId();
		title = post.getTitle();
		introduce = post.getIntroduce();
		loveCount = post.getLoveCount();
		member = new MemberMainDTO(post.getMember());
		postThumbnail = new PostThumbnailDTO(post.getPostThumbnail());
	}
}
