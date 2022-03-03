package teamexpress.velo9.post.service;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import teamexpress.velo9.post.dto.PostDTO;
import teamexpress.velo9.post.domain.Post;
import teamexpress.velo9.post.domain.PostRepository;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {
	private PostRepository postRepository;

	@Override
	public Long save(PostDTO postDTO) {
		Post post = dtoToEntity(postDTO);

		postRepository.save(post);

		return post.getId();
	}
}
