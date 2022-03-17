package teamexpress.velo9.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import teamexpress.velo9.member.domain.MemberThumbnail;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberThumbnailDTO {

	private String uuid;
	private String name;
	private String path;

	public MemberThumbnail toMemberThumbnail() {

		MemberThumbnail memberThumbnail = MemberThumbnail.builder()
			.uuid(this.uuid)
			.path(this.path)
			.name(this.name)
			.build();

		return memberThumbnail;
	}
}
