package teamexpress.velo9.post.dto;

import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import teamexpress.velo9.post.domain.PostStatus;
import teamexpress.velo9.post.domain.Series;

@Data
public class SeriesDTO {

	public static final int SIZE = 3;

	private String seriesName;
	private List<PostSummaryDTO> posts;

	public SeriesDTO(Series series) {
		seriesName = series.getName();
		posts = series.getPosts().stream()
			.filter(p -> p.getStatus().equals(PostStatus.GENERAL))
			.map(PostSummaryDTO::new).limit(SIZE)
			.collect(Collectors.toList());
	}
}
