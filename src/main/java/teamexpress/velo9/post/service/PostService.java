package teamexpress.velo9.post.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import teamexpress.velo9.post.domain.Post;
import teamexpress.velo9.post.domain.PostRepository;
import teamexpress.velo9.post.domain.PostThumbnail;
import teamexpress.velo9.post.domain.PostThumbnailRepository;
import teamexpress.velo9.post.domain.Series;
import teamexpress.velo9.post.domain.SeriesRepository;
import teamexpress.velo9.post.dto.PostSaveDTO;
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
		Series series = null;

		if (postThumbnailSaveDTO != null) {
			postThumbnail = postThumbnailSaveDTO.toPostThumbnail();
		}

		if (postThumbnail != null) {
			postThumbnailRepository.save(postThumbnail);
		}

		Long seriesId = postSaveDTO.getSeriesId();

		if (seriesId != null) {
			series = seriesRepository.findById(seriesId).orElse(null);
		}

		Post post = postSaveDTO.toPost(postThumbnail, series);

		postRepository.save(post);

		return post.getId();
	}

}
