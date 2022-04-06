package teamexpress.velo9.post.dto;

import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.NoArgsConstructor;
import teamexpress.velo9.member.domain.Member;
import teamexpress.velo9.post.domain.Post;
import teamexpress.velo9.post.domain.PostAccess;
import teamexpress.velo9.post.domain.PostStatus;
import teamexpress.velo9.post.domain.PostTag;
import teamexpress.velo9.post.domain.PostThumbnail;
import teamexpress.velo9.post.domain.Series;

@Data
@NoArgsConstructor
public class PostSaveDTO {

	private static final int MAX = 150;
	private static final int FIRST_INDEX = 0;

	private Long postId;
	private String title;
	private String introduce;
	private String content;
	private String access;

	private Long memberId;
	private Long seriesId;
	private List<String> tagNames;

	private PostThumbnailDTO thumbnail;
	private TemporaryPostReadDTO temporary;

	public PostSaveDTO(Post post, List<PostTag> postTags) {
		this.postId = post.getId();
		this.title = post.getTitle();
		this.introduce = post.getIntroduce();
		this.content = post.getContent();

		this.memberId = post.getMember().getId();

		if (post.getAccess() != null) {
			this.access = post.getAccess().name();
		}

		if (post.getSeries() != null) {
			this.seriesId = post.getSeries().getId();
		}

		this.tagNames = postTags.stream()
			.map(postTag -> postTag.getTag().getName())
			.collect(Collectors.toList());

		this.thumbnail = new PostThumbnailDTO(post.getPostThumbnail());

		if (post.getTemporaryPost() != null) {
			this.temporary = new TemporaryPostReadDTO(post.getTemporaryPost());
		}

	}

	public Post toPost(Member member, Series series, PostThumbnail postThumbnail) {
		setIntroduce();
		setAccess();

		return Post.builder()
			.title(this.title)
			.introduce(this.introduce)
			.content(this.content)
			.status(PostStatus.GENERAL)
			.access(PostAccess.valueOf(this.access))
			.member(member)
			.series(series)
			.postThumbnail(postThumbnail)
			.temporaryPost(null)
			.build();
	}

	private void setIntroduce() {
		if (!isIntroduceNull()) {
			return;
		}
		if (smallerThanMax(this.content)) {
			this.introduce = this.content;
			return;
		}
		this.introduce = this.content.substring(FIRST_INDEX, MAX);
	}

	private void setAccess() {
		if (this.access == null) {
			this.access = PostAccess.PUBLIC.name();
		}
	}

	private boolean smallerThanMax(String content) {
		return content.length() < MAX;
	}

	private boolean isIntroduceNull() {
		return this.introduce == null;
	}
}
