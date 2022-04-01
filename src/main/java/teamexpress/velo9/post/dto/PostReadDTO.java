package teamexpress.velo9.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import teamexpress.velo9.post.domain.Post;

@Data
public class PostReadDTO {

	private Long id;
	private String title;
	private String introduce;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd-HH-mm-ss", timezone = "Asia/Seoul")
	private LocalDateTime createdDate;
	private int replyCount;
	private PostThumbnailDTO thumbnail;
	private List<TagDTO> postTags;

	public PostReadDTO(Post post) {
		id = post.getId();
		title = post.getTitle();
		introduce = post.getIntroduce();
		createdDate = post.getCreatedDate();
		replyCount = post.getReplyCount();
		thumbnail = new PostThumbnailDTO(post.getPostThumbnail());
		postTags = post.getPostTags().stream()
			.map(TagDTO::new)
			.collect(Collectors.toList());
	}
}
