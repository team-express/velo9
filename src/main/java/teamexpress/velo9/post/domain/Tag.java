package teamexpress.velo9.post.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamexpress.velo9.common.domain.BaseEntity;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Tag extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "tag_id")
	private Long id;
	private String name;

	@ManyToMany
	@JoinTable(name = "post_tag",
		joinColumns = @JoinColumn(name = "tag_id"),
		inverseJoinColumns = @JoinColumn(name = "post_id"))
	@JsonIgnore
	private List<Post> posts = new ArrayList<>();
}
