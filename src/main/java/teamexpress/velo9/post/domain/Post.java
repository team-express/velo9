package teamexpress.velo9.post.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import teamexpress.velo9.member.domain.Member;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Post {

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

	@OneToMany(mappedBy = "post")
	@JsonIgnore
	private List<PostTag> postTags = new ArrayList<>();

	@Enumerated(EnumType.STRING)
	private PostStatus status;

	@CreatedDate
	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@LastModifiedDate
	@Column(name = "updated_date")
	private LocalDateTime updatedDate;
}
