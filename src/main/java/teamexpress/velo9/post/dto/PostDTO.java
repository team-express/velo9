package teamexpress.velo9.post.dto;

import lombok.Data;
import teamexpress.velo9.post.domain.PostStatus;

@Data
public class PostDTO {
	private Long id;
	private String title;
	private String introduce;
	private String content;

	private PostStatus status;

	public PostDTO() {
		this.status = PostStatus.GENERAL;
	}
}
