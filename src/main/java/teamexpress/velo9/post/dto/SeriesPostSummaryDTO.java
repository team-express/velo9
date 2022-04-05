package teamexpress.velo9.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import teamexpress.velo9.post.domain.Post;

@Data
public class SeriesPostSummaryDTO {

	private Long id;
	private String title;
	private String seriesName;
	private String introduce;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd-HH-mm-ss", timezone = "Asia/Seoul")
	private LocalDateTime createdDate;
	private PostThumbnailDTO thumbnail;
	private List<String> postTags;

	public SeriesPostSummaryDTO(Post post) {
		id = post.getId();
		title = post.getTitle();
		seriesName = post.getSeries().getName();
		introduce = post.getIntroduce();
		createdDate = post.getCreatedDate();
		thumbnail = new PostThumbnailDTO(post.getPostThumbnail());
		postTags = post.getPostTags().stream()
			.map(postTag -> postTag.getTag().getName())
			.collect(Collectors.toList());
	}
}

