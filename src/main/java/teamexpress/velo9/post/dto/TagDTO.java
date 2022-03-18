package teamexpress.velo9.post.dto;

import lombok.Data;
import teamexpress.velo9.post.domain.PostTag;

@Data
public class TagDTO {

	String tagName;

	public TagDTO(PostTag postTag) {
		tagName = postTag.getTag().getName();
	}
}
