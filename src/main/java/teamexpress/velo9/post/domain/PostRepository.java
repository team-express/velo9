package teamexpress.velo9.post.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import teamexpress.velo9.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
