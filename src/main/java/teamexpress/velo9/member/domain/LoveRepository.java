package teamexpress.velo9.member.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import teamexpress.velo9.post.domain.Post;

public interface LoveRepository extends JpaRepository<Love, Long> {
	Optional<Love> findByPostAndMember(Post post, Member member);
}
