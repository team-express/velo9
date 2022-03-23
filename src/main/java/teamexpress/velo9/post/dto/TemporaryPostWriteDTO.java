package teamexpress.velo9.post.dto;

import java.time.LocalDateTime;
import lombok.Data;
import teamexpress.velo9.member.domain.Member;
import teamexpress.velo9.post.domain.Post;
import teamexpress.velo9.post.domain.PostStatus;

@Data
public class TemporaryPostWriteDTO {

	private Long id;
	private String title;
	private String content;

	private Long memberId;
	//private Long postId;

	public Post toPost(Member member, LocalDateTime createdDate) {
		return Post.builder()
			.id(this.id)
			.title(this.title)
			.content(this.content)
			.status(PostStatus.TEMPORARY)
			.member(member)
			.createdDate(createdDate)
			.build();
	}
}
