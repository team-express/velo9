package teamexpress.velo9.post.domain;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import teamexpress.velo9.member.domain.Member;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
	@Query("select p.createdDate from Post p where p.id = :id")
	LocalDateTime getCreatedDate(@Param("id") Long id);

	int countByMemberAndStatus(Member member, PostStatus status);

	@Query("update Post p set p.temporaryPost = :tmp where p.id = :id")
	@Modifying
	void updateTempPost(@Param("id") Long id, @Param("tmp") TemporaryPost temporaryPost);

	@Query("select p from Post p where p.member.id = :id and p.status =:status")
	List<Post> getTempSavedPost(@Param("id") Long id, @Param("status") PostStatus status);

	@Query("update Post p set p.loveCount = :loveCount where p = :post")
	@Modifying
	void updateLoveCount(@Param("post") Post post, @Param("loveCount") int loveCount);

	@Query("update Post p set p.viewCount = :viewCount where p = :post")
	@Modifying
	void updateViewCount(@Param("post") Post post, @Param("viewCount") int viewCount);
}
