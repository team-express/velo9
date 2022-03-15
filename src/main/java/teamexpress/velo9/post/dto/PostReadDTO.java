package teamexpress.velo9.post.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import teamexpress.velo9.post.domain.Post;

@Data
public class PostReadDTO {

	private String title;
	private String introduce;
	private LocalDateTime createdDate;
	private int replyCount;
	private String thumbnailPath;
	private String thumbnailUuid;
	private String thumbnailName;
	private List<TagDTO> postTags;

	public PostReadDTO(Post post) {
		title = post.getTitle();
		introduce = post.getIntroduce();
		createdDate = post.getCreatedDate();
		replyCount = post.getReplyCount();
		thumbnailPath = post.getPostThumbnail().getPath();
		thumbnailUuid = post.getPostThumbnail().getUuid();
		thumbnailName = post.getPostThumbnail().getName();
		postTags = post.getPostTags().stream()
			.map(TagDTO::new)
			.collect(Collectors.toList());
	}
}
