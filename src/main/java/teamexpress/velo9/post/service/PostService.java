package teamexpress.velo9.post.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import teamexpress.velo9.post.domain.Post;
import teamexpress.velo9.post.domain.PostRepository;
import teamexpress.velo9.post.domain.PostStatus;
import teamexpress.velo9.post.dto.PostDTO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
	private final PostRepository postRepository;

	@Transactional
	public Long write(PostDTO postDTO) {
		Post post = dtoToEntity(postDTO);
		postRepository.save(post);
		return post.getId();
	}

	private static Post dtoToEntity(PostDTO postDTO) {
		postDTO.rearrangeIntroduce();

		Post post = Post.builder()
			.id(postDTO.getId())
			.title(postDTO.getTitle())
			.introduce(postDTO.getIntroduce())
			.content(postDTO.getContent())
			.status(PostStatus.GENERAL)
			.build();

		return post;
	}
}
