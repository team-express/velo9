package teamexpress.velo9.post.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface PostThumbnailRepository extends JpaRepository<PostThumbnail, Long> {
	@Transactional
	@Modifying
	void deleteAllByPost(Post post);

	PostThumbnail findByPost(Post post);
}
