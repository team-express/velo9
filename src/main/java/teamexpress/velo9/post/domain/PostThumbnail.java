package teamexpress.velo9.post.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamexpress.velo9.common.domain.BaseEntity;

@Entity(name = "post_thumbnail")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostThumbnail extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "post_thumbnail_id")
	private Long id;
	private String uuid;
	private String name;
	private String path;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private Post post;
}
