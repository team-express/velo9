package teamexpress.velo9.post.dto;

import lombok.Data;
import teamexpress.velo9.member.domain.Member;
import teamexpress.velo9.post.domain.Post;
import teamexpress.velo9.post.domain.PostStatus;

@Data
public class TemporaryPostWriteDTO {

	private String title;
	private String content;

	private Long memberId;
	//private Long postId;

	public Post toPost(Member member) {
		return Post.builder()
			//.id(this.id)
			.title(this.title)
			//.introduce(this.introduce)
			.content(this.content)
			.status(PostStatus.TEMPORARY)
			//.access(PostAccess.valueOf(this.access))
			.member(member)
			//.series(series)
			//.postThumbnail(postThumbnail)
			//.createdDate(createdDate)
			.build();
	}
}
