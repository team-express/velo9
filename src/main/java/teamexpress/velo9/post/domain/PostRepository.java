package teamexpress.velo9.post.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
    @Query("select p.createdDate from Post p where p.id = :id")
    LocalDateTime getCreatedDate(@Param("id") Long id);

    @Query("select p from Post p where p.member.id = :id and p.status =:status")
    List<Post> getTempSavedPost(@Param("id") Long id, @Param("status") PostStatus status);

}
