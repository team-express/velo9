package teamexpress.velo9.post.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import teamexpress.velo9.post.dto.PostReadDTO;

public interface PostRepositoryCustom {

	Slice<PostReadDTO> findPost(String blogTitle, Pageable pageable);
}
