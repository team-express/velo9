package teamexpress.velo9.post.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import teamexpress.velo9.post.domain.Series;

@Data
public class SeriesDTO {

	public static final int SIZE = 3;

	private String seriesName;
	private List<PostDTO> posts;

	public SeriesDTO() {
	}

	@QueryProjection
	public SeriesDTO(Series series) {
		seriesName = series.getName();
		posts = series.getPosts().stream().map(PostDTO::new).limit(SIZE)
			.collect(Collectors.toList());
	}
}
