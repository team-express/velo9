package teamexpress.velo9.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import teamexpress.velo9.post.domain.Post;

@Data
public class ReadDTO {

	private String title;
	private String seriesName;
	private String content;
	private int loveCount;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd-HH-mm-ss", timezone = "Asia/Seoul")
	private LocalDateTime createdDate;
	private PostMemberDTO memberDTO;
	private List<TagDTO> postTags;
	private String prevTitle;
	private String nextTitle;

	public ReadDTO(Post post) {
		title = post.getTitle();
		seriesName = seriesNullCheck(post);
		content = post.getContent();
		loveCount = post.getLoveCount();
		createdDate = post.getCreatedDate();
		memberDTO = new PostMemberDTO(post.getMember());
		postTags = post.getPostTags().stream().map(TagDTO::new).collect(Collectors.toList());
	}

	private String seriesNullCheck(Post post) {
		return post.getSeries() == null ? null : post.getSeries().getName();
	}
}
