package teamexpress.velo9.post.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TagRepository extends JpaRepository<Tag, Long>, TagRepositoryCustom {
	@Query("select t.name from Tag t")
	List<String> getTagNames();

	Tag findByName(String name);
}
