package teamexpress.velo9.post.dto;

import lombok.Data;
import teamexpress.velo9.post.domain.Post;

@Data
public class PostSummaryDTO {

	private Long postId;
	private String title;
	private String introduce;
	private PostThumbnailDTO thumbnail;

	public PostSummaryDTO(Post post) {
		postId = post.getId();
		title = post.getTitle();
		introduce = post.getIntroduce();
		thumbnail = new PostThumbnailDTO(post.getPostThumbnail());
	}
}
