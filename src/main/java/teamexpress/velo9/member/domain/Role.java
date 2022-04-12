package teamexpress.velo9.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
	ROLE_USER("ROLE_USER");

	private final String key;
}
