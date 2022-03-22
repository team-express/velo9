package teamexpress.velo9.post.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PostRepositoryCustom {
	Slice<Post> findReadPost(String nickname, Pageable pageable);
}
