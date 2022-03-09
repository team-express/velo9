package teamexpress.velo9.post.service;

import teamexpress.velo9.post.domain.Post;
import teamexpress.velo9.post.dto.PostDTO;

public interface PostService {
	Long save(PostDTO postDTO);

	default Post dtoToEntity(PostDTO postDTO) {
		return Post.builder()
			.id(postDTO.getId())
			.title(postDTO.getTitle())
			.introduce(postDTO.getIntroduce())
			.content(postDTO.getContent())
			.build();
	}
}
