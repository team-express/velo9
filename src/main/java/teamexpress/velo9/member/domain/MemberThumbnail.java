package teamexpress.velo9.member.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamexpress.velo9.common.domain.BaseEntity;

@Entity(name = "member_thumbnail")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
