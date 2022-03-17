package teamexpress.velo9.post.dto;

import lombok.Data;
import teamexpress.velo9.post.domain.Post;

@Data
public class PostDTO {

	private String title;
	private String introduce;
	private String path;
	private String uuid;
	private String name;

	public PostDTO(Post post) {
		title = post.getTitle();
		introduce = post.getIntroduce();
		path = post.getPostThumbnail().getPath();
		uuid = post.getPostThumbnail().getUuid();
		name = post.getPostThumbnail().getName();
	}
}
