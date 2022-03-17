package teamexpress.velo9.post.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SeriesRepository extends JpaRepository<Series, Long>, SeriesRepositoryCustom {

}
