package teamexpress.velo9.post.dto;

import lombok.Data;
import teamexpress.velo9.post.domain.Post;

@Data
public class PostSeriesDTO {

	private String title;
	private String introduce;
	private String thumbnailPath;
	private String thumbnailUuid;
	private String thumbnailName;

	public PostSeriesDTO(Post post) {
		title = post.getTitle();
		introduce = post.getIntroduce();
		thumbnailPath = post.getPostThumbnail().getPath();
		thumbnailUuid = post.getPostThumbnail().getUuid();
		thumbnailName = post.getPostThumbnail().getName();
	}
}
