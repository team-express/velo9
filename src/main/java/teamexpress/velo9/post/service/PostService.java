package teamexpress.velo9.post.service;

import teamexpress.velo9.post.dto.PostDTO;
import teamexpress.velo9.post.domain.Post;

public interface PostService {
	Long save(PostDTO postDTO);

	default Post dtoToEntity(PostDTO postDTO){
		return new Post(postDTO);
	}
}
