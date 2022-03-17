package teamexpress.velo9.post.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import teamexpress.velo9.post.dto.SeriesDTO;

public interface SeriesRepositoryCustom {
	Slice<SeriesDTO> findPostBySeriesName(String nickname, Pageable pageable);
}
