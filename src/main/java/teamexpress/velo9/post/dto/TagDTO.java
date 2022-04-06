package teamexpress.velo9.post.dto;

import lombok.Data;
import teamexpress.velo9.post.domain.Tag;

@Data
public class TagDTO {

	private Long id;
	private String name;

	public TagDTO(Tag tag) {
		this.id = tag.getId();
		this.name = tag.getName();
	}
}
