package teamexpress.velo9.post.service;

import org.springframework.http.HttpHeaders;
import org.springframework.web.multipart.MultipartFile;

import teamexpress.velo9.post.domain.Post;
import teamexpress.velo9.post.domain.PostThumbnail;
import teamexpress.velo9.post.dto.PostThumbnailDTO;

public interface PostThumbnailService {
	void register(PostThumbnailDTO postThumbnailDTO);

	PostThumbnailDTO upload(MultipartFile uploadFile);

	void delete(Long postId);

	void deleteFile(String fileName);

	PostThumbnailDTO get(Long postId);

	byte[] getImage(String fileName);

	HttpHeaders getHeader(String fileName);

	default PostThumbnail dtoToEntity(PostThumbnailDTO postThumbnailDTO, Post post) {
		return new PostThumbnail(postThumbnailDTO.getPostId(), postThumbnailDTO.getUuid(),
			postThumbnailDTO.getName(), postThumbnailDTO.getPath(), post);
	}
}
