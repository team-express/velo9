package teamexpress.velo9.member.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import teamexpress.velo9.member.domain.Member;
import teamexpress.velo9.member.domain.Role;

@Data
public class MemberSignupDTO {

	@NotBlank
	private String username;
	@NotBlank
	private String password;
	@NotBlank
	private String nickname;
	@NotBlank
	@Email
	private String email;
	private Role role;

	public Member toMember() {

		return Member.builder()
			.username(username)
			.password(password)
			.nickname(nickname)
			.email(email)
			.role(Role.ROLE_USER)
			.build();
	}
}
