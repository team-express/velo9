package teamexpress.velo9.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import teamexpress.velo9.post.domain.PostThumbnail;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostThumbnailDTO {

	private String uuid;
	private String name;
	private String path;


	public PostThumbnailDTO(PostThumbnail postThumbnail) {
		this.uuid = postThumbnail.getUuid();
		this.name = postThumbnail.getName();
		this.path = postThumbnail.getPath();
	}

	public PostThumbnail toPostThumbnail() {

		return PostThumbnail.builder()
			.uuid(this.uuid)
			.path(this.path)
			.name(this.name)
			.build();
	}
}
