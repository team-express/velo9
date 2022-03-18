package teamexpress.velo9.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamexpress.velo9.member.domain.Member;
import teamexpress.velo9.member.domain.MemberRepository;
import teamexpress.velo9.post.domain.Post;
import teamexpress.velo9.post.domain.PostRepository;
import teamexpress.velo9.post.domain.PostThumbnail;
import teamexpress.velo9.post.domain.PostThumbnailRepository;
import teamexpress.velo9.post.domain.Series;
import teamexpress.velo9.post.domain.SeriesRepository;
import teamexpress.velo9.post.dto.PostReadDTO;
import teamexpress.velo9.post.dto.PostSaveDTO;
import teamexpress.velo9.post.dto.PostThumbnailDTO;
import teamexpress.velo9.post.dto.SeriesDTO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

	private final PostRepository postRepository;
	private final PostThumbnailRepository postThumbnailRepository;
	private final SeriesRepository seriesRepository;
	private final MemberRepository memberRepository;

	private PostThumbnail getPostThumbnail(PostThumbnailDTO postThumbnailDTO) {
		PostThumbnail postThumbnail = null;

		if (postThumbnailDTO != null) {
			postThumbnail = postThumbnailDTO.toPostThumbnail();
		}

		return postThumbnail;
	}

	private Series getSeries(Long seriesId) {
		Series series = null;

		if (seriesId != null) {
			series = seriesRepository.findById(seriesId).orElse(null);
		}

		return series;
	}

	private Member getMember(Long memberId) {
		if (memberId == null) {
			throw new NullPointerException("no member is NOT NULL!!!");
		}

		return memberRepository.findById(memberId)
			.orElseThrow(() -> new NullPointerException("no member"));
	}

	@Transactional
	public Long write(PostSaveDTO postSaveDTO) {

		PostThumbnail postThumbnail = getPostThumbnail(postSaveDTO.getPostThumbnailDTO());
		Series series = getSeries(postSaveDTO.getSeriesId());
		Member member = getMember(postSaveDTO.getMemberId());

		if (postThumbnail != null) {
			postThumbnailRepository.save(postThumbnail);
		}

		Post post = postSaveDTO.toPost(postThumbnail, series, member);

		postRepository.save(post);

		return post.getId();
	}

	public Slice<SeriesDTO> findSeries(String nickname, Pageable pageable) {
		return seriesRepository.findPostBySeriesName(nickname, pageable);
	}
	public Slice<PostReadDTO> findPost(String nickname, Pageable pageable) {
		return postRepository.findPost(nickname, pageable);
	}
}
