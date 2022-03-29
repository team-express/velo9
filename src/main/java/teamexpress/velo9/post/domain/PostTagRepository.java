package teamexpress.velo9.post.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {
	void deleteAllByPost(Post post);

	int countByTag(Tag tag);
}
