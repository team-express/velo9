package teamexpress.velo9.member.security.oauth;

import java.util.Map;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import teamexpress.velo9.member.domain.Member;
import teamexpress.velo9.member.domain.Role;

@Getter
public class OAuthAttributes {

	private Map<String, Object> attributes;
	private String nameAttributeKey;
	private String email;
	private String nickname;
	private String picture;

	@Builder
	public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey,
		String email, String nickname, String picture) {
		this.attributes = attributes;
		this.nameAttributeKey = nameAttributeKey;
		this.nickname = nickname;
		this.email = email;
		this.picture = picture;
	}

	public static OAuthAttributes of(String registrationId, String userNameAttributeName,
		Map<String, Object> attributes) {

		if ("github".equals(registrationId)) {
			return ofGithub("id", attributes);
		}
		return ofGoogle(userNameAttributeName, attributes);
	}

	private static OAuthAttributes ofGithub(String userNameAttributeName,
		Map<String, Object> attributes) {
		return OAuthAttributes.builder()
			.email((String) attributes.get("email"))
			.picture((String) attributes.get("avatar_url"))
			.nickname(UUID.randomUUID().toString())
			.attributes(attributes)
			.nameAttributeKey(userNameAttributeName)
			.build();
	}

	private static OAuthAttributes ofGoogle(String userNameAttributeName,
		Map<String, Object> attributes) {
		return OAuthAttributes.builder()
			.email((String) attributes.get("email"))
			.picture((String) attributes.get("picture"))
			.nickname(UUID.randomUUID().toString())
			.attributes(attributes)
			.nameAttributeKey(userNameAttributeName)
			.build();
	}

	public Member toEntity() {
		return Member.builder()
			.email(email)
			.nickname(nickname)
			.role(Role.ROLE_USER)
			.build();
	}
}
