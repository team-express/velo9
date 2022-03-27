package teamexpress.velo9.post.dto;

import lombok.Data;
import teamexpress.velo9.member.domain.Member;

@Data
public class PostMemberThumbnailDTO {

	private String uuid;
	private String name;
	private String path;

	public PostMemberThumbnailDTO(Member member) {
		this.uuid = member.getMemberThumbnail().getUuid();
		this.name = member.getMemberThumbnail().getName();
		this.path = member.getMemberThumbnail().getPath();
	}
}
