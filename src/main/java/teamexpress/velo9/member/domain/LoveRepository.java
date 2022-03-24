package teamexpress.velo9.member.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import teamexpress.velo9.post.domain.Post;

public interface LoveRepository extends JpaRepository<Love, Long> {
	Optional<Love> findByPostAndMember(Post post, Member member);

	int countByPost(Post post);

	// 네이티브 쿼리로 특정 회원의 좋아요 Post 조회
	@Query(value = " SELECT p" +
		" FROM Post p" +
		" JOIN Love l" +
		" ON p.id = l.post.id" +
		" JOIN Member m" +
		" ON m.id = l.member.id" +
		" WHERE p.member.id = :memberId AND p.id < :lastPostId")
	List<Post> findByJoinLove(@Param("memberId") Long memberId, @Param("lastPostId") Long lastPostId, Pageable pageable);

}

