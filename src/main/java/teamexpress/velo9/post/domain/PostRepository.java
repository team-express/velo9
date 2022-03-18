package teamexpress.velo9.post.domain;

import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

	@Query("select p.createdDate from Post p where p.id = :id")
	LocalDateTime getCreatedDate(@Param("id") Long id);
}
