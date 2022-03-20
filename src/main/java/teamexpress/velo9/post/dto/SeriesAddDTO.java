package teamexpress.velo9.post.dto;

import lombok.Data;
import teamexpress.velo9.member.domain.Member;
import teamexpress.velo9.post.domain.Series;

@Data
public class SeriesAddDTO {

	private Long id;
	private String name;
	private Long memberId;

	public Series toSeries(Member member) {
		return Series.builder()
			.id(this.id)
			.name(this.name)
			.member(member)
			.build();
	}
}
