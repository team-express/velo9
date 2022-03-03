package teamexpress.velo9.post.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends BaseEntity{

	@Id
	@GeneratedValue
	@Column(name = "post_id")
	private Long id;
	private String title;
	private String introduce;
	private String content;

	//private Long member_id;
	//private Long series_id;

	//private int likeCount;
	//private int replyCount;
	//private int viewCount;
}
