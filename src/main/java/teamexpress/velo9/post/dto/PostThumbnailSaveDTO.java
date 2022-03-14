package teamexpress.velo9.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import teamexpress.velo9.post.domain.PostThumbnail;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostThumbnailSaveDTO {
	private String uuid;
	private String name;
	private String path;

	public PostThumbnail toPostThumbnail() {

		PostThumbnail postThumbnail = PostThumbnail.builder()
			.uuid(this.uuid)
			.path(this.path)
			.name(this.name)
			.build();

		return postThumbnail;
	}
}
