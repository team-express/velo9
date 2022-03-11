package teamexpress.velo9.member.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import teamexpress.velo9.common.domain.BaseEntity;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
public class MemberThumbnail extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_thumbnail_id")
	private Long id;
	private String uuid;
	private String name;
}
