package teamexpress.velo9.member.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;

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
}
