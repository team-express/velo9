package teamexpress.velo9.post.service;

import teamexpress.velo9.post.domain.Post;
import teamexpress.velo9.post.domain.PostThumbnail;
import teamexpress.velo9.post.dto.PostThumbnailDTO;

public interface PostThumbnailService {
	void register(PostThumbnailDTO postThumbnailDTO);

	void delete(Long post_id);

	PostThumbnailDTO get(Long post_id);

	default PostThumbnail dtoToEntity(PostThumbnailDTO postThumbnailDTO, Post post) {
		return new PostThumbnail(postThumbnailDTO.getPost_id(), postThumbnailDTO.getUuid(),
			postThumbnailDTO.getName(), postThumbnailDTO.getPath(), post);
	}
}
