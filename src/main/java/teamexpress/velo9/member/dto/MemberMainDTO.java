package teamexpress.velo9.member.dto;

import lombok.Data;
import teamexpress.velo9.member.domain.Member;

@Data
public class MemberMainDTO {

	private String nickname;
	private MemberThumbnailDTO memberThumbnail;

	public MemberMainDTO(Member member) {
		nickname = member.getNickname();
		memberThumbnail = new MemberThumbnailDTO(member.getMemberThumbnail());
	}
}
