package teamexpress.velo9.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostThumbnailFileDTO {
	private String uuid;
	private String name;
	private String path;
}
