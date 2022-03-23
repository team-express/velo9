package teamexpress.velo9.post.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
	@Query("select p.createdDate from Post p where p.id = :id")
	LocalDateTime getCreatedDate(@Param("id") Long id);

	@Query("select p from Post p where p.member.id = :id and p.status =:status")
	List<Post> getTempSavedPost(@Param("id") Long id, @Param("status") PostStatus status);

	@Query("update Post p set p.loveCount = :loveCount")
	@Modifying
	void updateLoveCount(@Param("loveCount") int loveCount);

	@Query("update Post p set p.viewCount = p.viewCount+1")
	@Modifying
	void plusViewCount();
}
