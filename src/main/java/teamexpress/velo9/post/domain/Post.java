package teamexpress.velo9.post.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamexpress.velo9.member.domain.Member;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post{

	@Id
	@GeneratedValue
	@Column(name = "post_id")
	private Long id;
	private String title;
	private String introduce;
	private String content;
	private int likeCount;
	private int replyCount;
	private int viewCount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "series_id")
	private Series series;

	@OneToOne(mappedBy = "post")
	private PostThumbnail postThumbnail;

	@ManyToMany(mappedBy = "posts")
	private List<Tag> tags = new ArrayList<>();

	@Enumerated(EnumType.STRING)
	private PostStatus status;

	@CreatedDate
	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@LastModifiedDate
	@Column(name = "updated_date")
	private LocalDateTime updatedDate;
}
