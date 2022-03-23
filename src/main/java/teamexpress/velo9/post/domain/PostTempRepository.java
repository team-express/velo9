package teamexpress.velo9.post.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.stream.Collectors;

public interface PostTempRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

	// 목적: status 조건, member id 조건으로 post 조회
	// 리뷰요청: status 조건 설정 방법
	@Query("select p from Post p where p.status = :TEMPORARY and p.member.id = :id")

	// postService에서 호출
	List<Post> getTempSavedPost(@Param("id") Long id);
}
