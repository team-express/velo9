package teamexpress.velo9.post.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import teamexpress.velo9.post.domain.Series;

@Data
@NoArgsConstructor
public class SeriesReadDTO {

	private Long id;
	private String name;
	private Long memberId;

	public SeriesReadDTO(Series series) {
		this.id = series.getId();
		this.name = series.getName();
		this.memberId = series.getMember().getId();
	}
}
