package teamexpress.velo9.post.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import teamexpress.velo9.post.domain.Post;
import teamexpress.velo9.post.domain.PostRepository;
import teamexpress.velo9.post.domain.PostStatus;
import teamexpress.velo9.post.domain.PostThumbnail;
import teamexpress.velo9.post.domain.PostThumbnailRepository;
import teamexpress.velo9.post.dto.PostDTO;
import teamexpress.velo9.post.dto.PostThumbnailFileDTO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
	private final PostRepository postRepository;
	private final PostThumbnailRepository postThumbnailRepository;

	@Transactional
	public Long write(PostDTO postDTO) {

		PostThumbnail postThumbnail = dtoToEntity(postDTO.getPostThumbnailFileDTO());

		if (postThumbnail != null) {
			postThumbnailRepository.save(postThumbnail);
		}

		Post post = dtoToEntity(postDTO, postThumbnail);

		postRepository.save(post);

		return post.getId();
	}

	private static PostThumbnail dtoToEntity(PostThumbnailFileDTO postThumbnailFileDTO) {
		if (postThumbnailFileDTO == null)
			return null;

		PostThumbnail postThumbnail = PostThumbnail.builder()
			.uuid(postThumbnailFileDTO.getUuid())
			.path(postThumbnailFileDTO.getPath())
			.name(postThumbnailFileDTO.getName())
			.build();

		return postThumbnail;
	}

	private static Post dtoToEntity(PostDTO postDTO, PostThumbnail postThumbnail) {
		postDTO.rearrangeIntroduce();

		Post post = Post.builder()
			.id(postDTO.getId())
			.title(postDTO.getTitle())
			.introduce(postDTO.getIntroduce())
			.content(postDTO.getContent())
			.status(PostStatus.GENERAL)
			.postThumbnail(postThumbnail)
			.build();

		return post;
	}
}
