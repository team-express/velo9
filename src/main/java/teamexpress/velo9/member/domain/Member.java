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
import teamexpress.velo9.post.domain.Series;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	private String username;
	private String nickname;
	private String password;
	private String email;
	private String introduce;
	@Column(name = "blog_title")
	private String blogTitle;
	@Column(name = "social_email")
	private String socialEmail;
	@Column(name = "social_github")
	private String socialGithub;

	@OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<Post> posts = new ArrayList<>();

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "member_thumbnail_id")
	private MemberThumbnail memberThumbnail;

	@OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<Look> looks = new ArrayList<>();

	@OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<Love> loves = new ArrayList<>();

	@Enumerated(value = EnumType.STRING)
	private Role role;

	@OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<Series> series = new ArrayList<>();

	public String getRoleKey() {
		return role.getKey();
	}

	public void uploadThumbnail(MemberThumbnail memberThumbnail) {
		this.memberThumbnail = memberThumbnail;
	}

	public Member edit(String nickname, String introduce, String blogTitle, String socialEmail, String socialGithub) {
		this.nickname = nickname;
		this.introduce = introduce;
		this.blogTitle = blogTitle;
		this.socialEmail = socialEmail;
		this.socialGithub = socialGithub;
		return this;
	}

	public void changePassword(String encodedPassword) {
		password = encodedPassword;
	}

	public void registerSocialMember(String username, String password, String nickname) {
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.blogTitle = nickname;
	}
}
