package teamexpress.velo9.post.dto;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import teamexpress.velo9.member.domain.Member;
import teamexpress.velo9.post.domain.Post;
import teamexpress.velo9.post.domain.PostAccess;
import teamexpress.velo9.post.domain.PostStatus;
import teamexpress.velo9.post.domain.PostThumbnail;
import teamexpress.velo9.post.domain.Series;

@Data
@NoArgsConstructor
public class PostSaveDTO {

	private Long postId;
	private String title;
	private String introduce;
	private String content;
	private String access;

	private Long seriesId;
	private List<String> tags;

	private String thumbnailFileName;
	private TemporaryPostReadDTO temporary;

	public Post toPost(Member member, Series series, PostThumbnail postThumbnail) {
		return Post.builder()
			.title(this.title)
			.introduce(this.introduce)
			.content(this.content)
			.status(PostStatus.GENERAL)
			.access(PostAccess.valueOf(this.access))
			.member(member)
			.series(series)
			.postThumbnail(postThumbnail)
			.build();
	}
}
