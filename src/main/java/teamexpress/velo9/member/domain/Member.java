package teamexpress.velo9.member.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
	@GeneratedValue
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

	@OneToMany(mappedBy = "member")
	@JsonIgnore
	private List<Post> posts = new ArrayList<>();

	@OneToOne(mappedBy = "member")
	private MemberThumbnail memberThumbnail;

	@OneToMany(mappedBy = "member")
	@JsonIgnore
	private List<Refer> refers = new ArrayList<>();
}
