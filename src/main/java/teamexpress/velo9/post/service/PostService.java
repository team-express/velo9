package teamexpress.velo9.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamexpress.velo9.post.domain.Post;
import teamexpress.velo9.post.domain.PostRepository;
import teamexpress.velo9.post.domain.PostThumbnail;
import teamexpress.velo9.post.domain.PostThumbnailRepository;
import teamexpress.velo9.post.domain.SeriesRepository;
import teamexpress.velo9.post.dto.PostSaveDTO;
import teamexpress.velo9.post.dto.SeriesDTO;
import teamexpress.velo9.post.dto.PostThumbnailSaveDTO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

	private final PostRepository postRepository;
	private final PostThumbnailRepository postThumbnailRepository;
	private final SeriesRepository seriesRepository;

	@Transactional
	public Long write(PostSaveDTO postSaveDTO) {

		PostThumbnailSaveDTO postThumbnailSaveDTO = postSaveDTO.getPostThumbnailSaveDTO();
		PostThumbnail postThumbnail = null;

		if (postThumbnailSaveDTO != null) {
			postThumbnail = postThumbnailSaveDTO.toPostThumbnail();
		}

		if (postThumbnail != null) {
			postThumbnailRepository.save(postThumbnail);
		}

		Post post = postSaveDTO.toPost(postThumbnail);

		postRepository.save(post);

		return post.getId();
	}

	public Slice<SeriesDTO> findSeries(String nickname, Pageable pageable) {
		return seriesRepository.findPostBySeriesName(nickname, pageable);
	}
}
