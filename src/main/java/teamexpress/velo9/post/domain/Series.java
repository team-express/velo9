package teamexpress.velo9.post.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamexpress.velo9.common.domain.BaseEntity;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Series extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "series_id")
	private Long id;
	private String name;

	@OneToMany(mappedBy = "series")
	@JsonIgnore
	private List<Post> posts = new ArrayList<>();
}
