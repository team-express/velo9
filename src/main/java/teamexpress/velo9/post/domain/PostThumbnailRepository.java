package teamexpress.velo9.post.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostThumbnailRepository extends JpaRepository<PostThumbnail, Long> {

	void deleteByPostId(Long postId);

	PostThumbnail findByPostId(Long postId);
}
