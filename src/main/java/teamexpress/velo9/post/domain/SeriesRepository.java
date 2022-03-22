package teamexpress.velo9.post.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import teamexpress.velo9.member.domain.Member;

public interface SeriesRepository extends JpaRepository<Series, Long>, SeriesRepositoryCustom {
	List<Series> findAllByMember(Member member);
}
