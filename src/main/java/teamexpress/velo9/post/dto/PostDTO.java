package teamexpress.velo9.post.dto;

import lombok.Data;

@Data
public class PostDTO {
	private Long id;
	private String title;
	private String introduce;
	private String content;
	//private Long thumbnailId;
	private String status;
}
