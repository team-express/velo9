package teamexpress.velo9.post.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.query.Param;
import teamexpress.velo9.post.dto.SearchCondition;

public interface PostRepositoryCustom {
	Slice<Post> findPost(String nickname, String tagName, Pageable pageable);
	Page<Post> search(SearchCondition condition, Pageable pageable);
	Slice<Post> findByJoinLove(Long memberId, Pageable pageable);
	Slice<Post> findByJoinLook(Long memberId, Pageable pageable);
	Post findReadPost(Long postId, String nickname);
	Slice<Post> findByJoinSeries(String nickname, String seriesName, Pageable pageable);
	List<Post> findPrevNextPost(Post findPost);
	void updateViewCount(@Param("post") Long postId);
	Optional<Post> findWritePost(Long id);
}
