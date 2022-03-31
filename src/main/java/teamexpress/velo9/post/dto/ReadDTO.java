package teamexpress.velo9.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import teamexpress.velo9.post.domain.Post;

@Data
public class ReadDTO {

	private String title;
	private String seriesName;
	private String content;
	private int loveCount;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd-HH-mm-ss", timezone = "Asia/Seoul")
	private LocalDateTime createdDate;
	private PostMemberDTO memberDTO;
	private List<TagDTO> postTags;
<<<<<<< HEAD
	//private TransferPostDTO transferPostDTO;

	public ReadDTO(Post post, Post prevPost, Post nextPost) {
		title = post.getTitle();
		seriesName = seriesNullCheck(post);
		content = post.getContent();
		loveCount = post.getLoveCount();
		createdDate = post.getCreatedDate();
		memberDTO = new PostMemberDTO(post.getMember());
		postTags = post.getPostTags().stream().map(TagDTO::new).collect(Collectors.toList());
		//transferPostDTO= new TransferPostDTO(prevPost, nextPost);
=======
	private String prevPost;
	private Long prevPostId;
	private String nextPost;
	private Long nextPostId;


	public ReadDTO(Post findPost, List<Post> pagePost) {
		title = findPost.getTitle();
		seriesName = seriesNullCheck(findPost);
		content = findPost.getContent();
		loveCount = findPost.getLoveCount();
		createdDate = findPost.getCreatedDate();
		memberDTO = new PostMemberDTO(findPost.getMember());
		pagePost.forEach(post -> {
			getPrevPost(findPost, post);
			getNextPost(findPost, post);
		});
>>>>>>> dcf2257c85765dae195be9689b7b7de1b9c80b0b
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
