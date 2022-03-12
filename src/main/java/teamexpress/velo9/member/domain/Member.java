package teamexpress.velo9.member.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamexpress.velo9.common.domain.BaseEntity;
import teamexpress.velo9.post.domain.Post;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	private String username;
	private String password;
	private String nickname;
	private String email;
	@Column(name = "blog_title")
	private String blogTitle;
	private String introduce;
	@Column(name = "social_email")
	private String socialEmail;
	@Column(name = "social_github")
	private String socialGithub;
	@Enumerated(value = EnumType.STRING)
	private Role role;

	@OneToMany(mappedBy = "member")
	@JsonIgnore
	private List<Post> posts = new ArrayList<>();

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "member_thumbnail_id")
	private MemberThumbnail memberThumbnail;

	@OneToMany(mappedBy = "member")
	@JsonIgnore
	private List<Refer> refers = new ArrayList<>();

	public String getRoleKey() {
		return role.getKey();
	}

	public Member update(String nickname) {
		this.nickname = nickname;
		return this;
	}
}
