package teamexpress.velo9.member.security;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import teamexpress.velo9.member.domain.Member;

public class MemberContext extends User {

	private final Member member;

	public MemberContext(Member member, Collection<? extends GrantedAuthority> authorities) {
		super(member.getUsername(), member.getPassword(), authorities);
		this.member = member;
	}

	public Member getMember() {
		return member;
	}
}
