package teamexpress.velo9.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamexpress.velo9.member.domain.Look;
import teamexpress.velo9.member.domain.LookRepository;
import teamexpress.velo9.member.domain.Love;
import teamexpress.velo9.member.domain.LoveRepository;
import teamexpress.velo9.member.domain.Member;
import teamexpress.velo9.member.domain.MemberRepository;
import teamexpress.velo9.post.domain.Post;
import teamexpress.velo9.post.domain.PostRepository;
import teamexpress.velo9.post.domain.PostStatus;
import teamexpress.velo9.post.domain.PostThumbnail;
import teamexpress.velo9.post.domain.PostThumbnailRepository;
import teamexpress.velo9.post.domain.Series;
import teamexpress.velo9.post.domain.SeriesRepository;
import teamexpress.velo9.post.dto.LookDTO;
import teamexpress.velo9.post.dto.LoveDTO;
import teamexpress.velo9.post.dto.PostReadDTO;
import teamexpress.velo9.post.dto.PostSaveDTO;
import teamexpress.velo9.post.dto.PostThumbnailDTO;
import teamexpress.velo9.post.dto.SeriesDTO;
import teamexpress.velo9.post.dto.TemporaryPostWriteDTO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

	private static final int MAX_TEMPORARY_COUNT = 5;

	private final PostRepository postRepository;
	private final PostThumbnailRepository postThumbnailRepository;
	private final SeriesRepository seriesRepository;
	private final MemberRepository memberRepository;
	private final LoveRepository loveRepository;
	private final LookRepository lookRepository;

	@Transactional
	public Long write(PostSaveDTO postSaveDTO) {

		PostThumbnail postThumbnail = getPostThumbnail(postSaveDTO.getPostThumbnailDTO());
		Series series = getSeries(postSaveDTO.getSeriesId());
		Member member = getMember(postSaveDTO.getMemberId());

		if (postThumbnail != null) {
			postThumbnailRepository.save(postThumbnail);
		}

		Post post = postSaveDTO.toPost(postThumbnail, series, member, postRepository.getCreatedDate(
			postSaveDTO.getId()));

		postRepository.save(post);

		return post.getId();
	}

	@Transactional
	public void writeNewTemporary(TemporaryPostWriteDTO temporaryPostWriteDTO) {

		checkCount(temporaryPostWriteDTO.getMemberId());

		Member member = getMember(temporaryPostWriteDTO.getMemberId());
		postRepository.save(temporaryPostWriteDTO.toPost(member));
	}

	public PostSaveDTO getPostById(Long id) {
		Post post = postRepository.findById(id).orElse(new Post());
		return new PostSaveDTO(post);
	}

	public Slice<SeriesDTO> findSeries(String nickname, Pageable pageable) {
		Slice<Series> seriesList = seriesRepository.findPostBySeriesName(nickname, pageable);
		return seriesList.map(SeriesDTO::new);
	}

	public Slice<PostReadDTO> findReadPost(String nickname, Pageable pageable) {
		Slice<Post> posts = postRepository.findReadPost(nickname, pageable);
		return posts.map(PostReadDTO::new);
	}

	@Transactional
	public void loveOrNot(LoveDTO loveDTO) {

		Member member = memberRepository.findById(loveDTO.getMemberId()).orElseThrow();
		Post post = postRepository.findById(loveDTO.getPostId()).orElseThrow();
		toggleLove(member, post);
	}

	@Transactional
	public void look(LookDTO lookDTO) {
		Member member = memberRepository.findById(lookDTO.getMemberId()).orElseThrow();
		Post post = postRepository.findById(lookDTO.getPostId()).orElseThrow();
		makeLook(member, post);
	}

	private PostThumbnail getPostThumbnail(PostThumbnailDTO postThumbnailDTO) {
		PostThumbnail postThumbnail = null;

		if (postThumbnailDTO != null) {
			postThumbnail = postThumbnailDTO.toPostThumbnail();
		}

		return postThumbnail;
	}

	private Member getMember(Long memberId) {
		if (memberId == null) {
			throw new NullPointerException("no member is NOT NULL!!!");
		}

		return memberRepository.findById(memberId)
			.orElseThrow(() -> new NullPointerException("no member"));
	}

	private Series getSeries(Long seriesId) {

		if (seriesId == null) {
			return null;
		}

		return seriesRepository.findById(seriesId).orElse(null);
	}

	private void toggleLove(Member member, Post post) {
		loveRepository.findByPostAndMember(post, member).ifPresentOrElse(
			loveRepository::delete,
			() -> loveRepository.save(
				Love.builder()
					.post(post)
					.member(member)
					.build()
			)
		);
	}

	private void makeLook(Member member, Post post) {
		if (lookRepository.findByPostAndMember(post, member).isEmpty()) {
			lookRepository.save(Look.builder()
				.post(post)
				.member(member)
				.build()
			);
		}
	}

	private void checkCount(Long memberId) {
		if (postRepository.countByMemberAndStatus(memberRepository.findById(memberId).orElseThrow(), PostStatus.TEMPORARY) >= MAX_TEMPORARY_COUNT) {
			throw new IllegalStateException("임시저장은 20개까지만 가능");
		}
	}
}
