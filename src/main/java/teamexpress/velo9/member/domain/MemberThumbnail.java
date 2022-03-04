package teamexpress.velo9.member.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

@Entity(name = "member_thumbnail")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberThumbnail extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "member_thumbnail_id")
	private Long id;
	private String uuid;
	private String name;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	@JsonIgnore
	private Member member;
}
