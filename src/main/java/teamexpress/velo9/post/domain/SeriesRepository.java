package teamexpress.velo9.post.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SeriesRepository extends JpaRepository<Series, Long>, SeriesRepositoryCustom {
	@Query("select s from Series s where s.member.id = :memberId")
	List<Series> findByMember(@Param("memberId") Long memberId);
}
