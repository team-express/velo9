package teamexpress.velo9.post.dto;

import lombok.Data;
import teamexpress.velo9.post.domain.Post;
import teamexpress.velo9.post.domain.PostStatus;
import teamexpress.velo9.post.domain.PostThumbnail;

@Data
public class PostSaveDTO {

	private static final int MAX = 150;
	private static final int FIRST_INDEX = 0;

	private Long id;
	private String title;
	private String introduce;
	private String content;
	private String status;

	private PostThumbnailSaveDTO postThumbnailSaveDTO;

	public Post toPost(PostThumbnail postThumbnail) {
		this.setIntroduce();

		Post post = Post.builder()
			.id(this.id)
			.title(this.title)
			.introduce(this.introduce)
			.content(this.content)
			.status(PostStatus.GENERAL)
			.postThumbnail(postThumbnail)
			.build();

		return post;
	}

	public boolean isIntroduceNull() {
		return this.introduce == null;
	}

	private void setIntroduce() {
		if (!isIntroduceNull()) {
			return;
		}
		if (smallerThanMax(this.content)) {
			this.introduce = this.content;
			return;
		}
		this.introduce = this.content.substring(FIRST_INDEX, MAX);
	}

	private boolean smallerThanMax(String content) {
		return content.length() < MAX;
	}
}
