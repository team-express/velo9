package teamexpress.velo9.post.dto;

import lombok.Data;
import teamexpress.velo9.post.domain.Post;

@Data
public class PostSummaryDTO {

	private String title;
	private String introduce;
	private PostThumbnailDTO thumbnail;

	public PostSummaryDTO(Post post) {
		title = post.getTitle();
		introduce = post.getIntroduce();
		thumbnail = new PostThumbnailDTO(post.getPostThumbnail());
	}
}