package teamexpress.velo9.post.service;

import teamexpress.velo9.post.dto.PostDTO;
import teamexpress.velo9.post.domain.Post;

public interface PostService {
	Long save(PostDTO postDTO);

	default Post dtoToEntity(PostDTO postDTO){
		Post post = Post.builder()
			.id(postDTO.getId())
			.title(postDTO.getTitle())
			.introduce(postDTO.getIntroduce())
			.content(postDTO.getContent())
			.build();

		return post;
	}
}
