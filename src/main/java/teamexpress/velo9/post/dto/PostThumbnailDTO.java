package teamexpress.velo9.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostThumbnailDTO {
	private String uuid;
	private String name;
	private String path;
	private Long postId;
}
