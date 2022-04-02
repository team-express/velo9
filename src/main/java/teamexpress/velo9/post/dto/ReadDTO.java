package teamexpress.velo9.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import teamexpress.velo9.post.domain.Post;

@Data
public class ReadDTO {

	private Long id;
	private String title;
	private String seriesName;
	private String content;
	private int loveCount;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd-HH-mm-ss", timezone = "Asia/Seoul")
	private LocalDateTime createdDate;
	private PostMemberDTO memberDTO;
	private List<TagDTO> postTags;
	private String prevPost;
	private Long prevPostId;
	private String nextPost;
	private Long nextPostId;


	public ReadDTO(Post findPost, List<Post> pagePost) {
		id = findPost.getId();
		title = findPost.getTitle();
		seriesName = seriesNullCheck(findPost);
		content = findPost.getContent();
		loveCount = findPost.getLoveCount();
		createdDate = findPost.getCreatedDate();
		memberDTO = new PostMemberDTO(findPost.getMember());
		postTags = findPost.getPostTags().stream()
			.map(TagDTO::new)
			.collect(Collectors.toList());
		pagePost.forEach(post -> {
			getPrevPost(findPost, post);
			getNextPost(findPost, post);
		});
	}

	private String seriesNullCheck(Post post) {
		return post.getSeries() == null ? "존재하지 않습니다." : post.getSeries().getName();
	}

	private void getPrevPost(Post findPost, Post post) {
		if (post.getId() < findPost.getId()) {
			prevPost = post.getTitle();
			prevPostId = post.getId();
		}
	}

	private void getNextPost(Post findPost, Post post) {
		if (post.getId() > findPost.getId()) {
			nextPost = post.getTitle();
			nextPostId = post.getId();
		}
	}

}
