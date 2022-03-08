package teamexpress.velo9.post.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import teamexpress.velo9.post.domain.Post;
import teamexpress.velo9.post.domain.PostRepository;
import teamexpress.velo9.post.domain.PostThumbnail;
import teamexpress.velo9.post.domain.PostThumbnailRepository;
import teamexpress.velo9.post.dto.PostThumbnailDTO;

@Service
@RequiredArgsConstructor
public class PostThumbnailServiceImpl implements PostThumbnailService {

	private final PostThumbnailRepository postThumbnailRepository;
	private final PostRepository postRepository;

	@Override
	public void register(PostThumbnailDTO postThumbnailDTO) {
		Post post = postRepository.findById(postThumbnailDTO.getPost_id()).get();

		PostThumbnail postThumbnail = dtoToEntity(postThumbnailDTO, post);

		postThumbnailRepository.save(postThumbnail);
	}

	@Override
	public void delete(Long post_id) {
		Post post = postRepository.findById(post_id).get();
		postThumbnailRepository.deleteAllByPost(post);
	}

	@Override
	public PostThumbnailDTO get(Long post_id) {
		Post post = postRepository.findById(post_id).get();
		PostThumbnail postThumbnail = postThumbnailRepository.findByPost(post);

		PostThumbnailDTO postThumbnailDTO = new PostThumbnailDTO();

		postThumbnailDTO.setName(postThumbnail.getName());
		postThumbnailDTO.setPath(postThumbnail.getPath());
		postThumbnailDTO.setUuid(postThumbnail.getUuid());

		return postThumbnailDTO;
	}
}
