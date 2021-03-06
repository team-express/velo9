package teamexpress.velo9.post.domain;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import teamexpress.velo9.post.dto.SearchCondition;

public interface TagRepositoryCustom {
	Page<Tag> searchTag(SearchCondition searchCondition, Pageable pageable);

	void deleteByIds(List<Long> ids);
}
