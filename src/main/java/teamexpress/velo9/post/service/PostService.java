package teamexpress.velo9.post.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import teamexpress.velo9.post.domain.TemporaryPost;
import teamexpress.velo9.post.domain.TemporaryPostRepository;
import teamexpress.velo9.post.dto.LookDTO;
import teamexpress.velo9.post.dto.LookPostDTO;
import teamexpress.velo9.post.dto.LoveDTO;
import teamexpress.velo9.post.dto.LovePostDTO;
import teamexpress.velo9.post.dto.PostMainDTO;
import teamexpress.velo9.post.dto.PostReadDTO;
import teamexpress.velo9.post.dto.PostSaveDTO;
import teamexpress.velo9.post.dto.PostThumbnailDTO;
import teamexpress.velo9.post.dto.ReadDTO;
import teamexpress.velo9.post.dto.SearchCondition;
import teamexpress.velo9.post.dto.SeriesDTO;
import teamexpress.velo9.post.dto.SeriesPostSummaryDTO;
import teamexpress.velo9.post.dto.TempSavedPostDTO;
import teamexpress.velo9.post.dto.TemporaryPostWriteDTO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

	private static final int MAX_TEMPORARY_COUNT = 20;

	private final PostRepository postRepository;
	private final PostThumbnailRepository postThumbnailRepository;
	private final SeriesRepository seriesRepository;
	private final MemberRepository memberRepository;
	private final LoveRepository loveRepository;
	private final LookRepository lookRepository;
	private final TemporaryPostRepository temporaryPostRepository;

	@Transactional
	public Long write(PostSaveDTO postSaveDTO) {
		PostThumbnail postThumbnail = getPostThumbnail(postSaveDTO.getPostThumbnailDTO());
		Series series = getSeries(postSaveDTO.getSeriesId());
		Member member = getMember(postSaveDTO.getMemberId());

		Optional.ofNullable(postThumbnail).ifPresent(postThumbnailRepository::save);

		Post post = postSaveDTO
			.toPost(postThumbnail, series, member, postRepository.getCreatedDate(postSaveDTO.getId()));

		postRepository.save(post);
		postRepository.updateLoveCount(post, loveRepository.countByPost(post));
		postRepository.updateViewCount(post, lookRepository.countByPost(post));

		return post.getId();
	}

	@Transactional
	public void writeTemporary(TemporaryPostWriteDTO temporaryPostWriteDTO) {
		Optional.ofNullable(temporaryPostWriteDTO.getId()).ifPresentOrElse(
			x->writeAlternativeTemporary(temporaryPostWriteDTO),
			()->writeNewTemporary(temporaryPostWriteDTO)
		);
	}

	@Transactional
	public void delete(Long id) {
		postRepository.deleteById(id);
	}

	public PostSaveDTO getPostById(Long id) {
		Post post = postRepository.findById(id).orElse(new Post());
		return new PostSaveDTO(post);
	}

	public Slice<SeriesDTO> findSeries(String nickname, Pageable pageable) {
		Slice<Series> seriesList = seriesRepository.findPostBySeriesName(nickname, pageable);
		return seriesList.map(SeriesDTO::new);
	}

	public Slice<PostReadDTO> findPost(String nickname, Pageable pageable) {
		Slice<Post> posts = postRepository.findPost(nickname, pageable);
		return posts.map(PostReadDTO::new);
	}

	@Transactional
	public void loveOrNot(LoveDTO loveDTO) {

		Member member = memberRepository.findById(loveDTO.getMemberId()).orElseThrow();
		Post post = postRepository.findById(loveDTO.getPostId()).orElseThrow();

		toggleLove(member, post);
		postRepository.updateLoveCount(post, loveRepository.countByPost(post));
	}

	@Transactional
	public void look(LookDTO lookDTO) {

		Member member = memberRepository.findById(lookDTO.getMemberId()).orElseThrow();
		Post post = postRepository.findById(lookDTO.getPostId()).orElseThrow();

		makeLook(member, post);
		postRepository.updateViewCount(post, lookRepository.countByPost(post));
	}

	public Page<PostMainDTO> searchMain(SearchCondition searchCondition, Pageable pageable) {
		return postRepository.search(searchCondition, pageable).map(PostMainDTO::new);
	}

	public List<TempSavedPostDTO> getTempSavedPost(Long id) {

		List<Post> findPosts = postRepository.getTempSavedPost(id, PostStatus.TEMPORARY);

		return findPosts.stream()
			.map(TempSavedPostDTO::new)
			.collect(Collectors.toList());
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

	private void writeAlternativeTemporary(TemporaryPostWriteDTO temporaryPostWriteDTO) {

		Post post = postRepository.findById(temporaryPostWriteDTO.getId()).orElseThrow();

		if (post.getStatus().equals(PostStatus.TEMPORARY)) {
			writeNewTemporary(temporaryPostWriteDTO);
			return;
		}

		Optional.ofNullable(post.getTemporaryPost()).ifPresent(tmp -> temporaryPostWriteDTO.setAlternativeId(tmp.getId()));

		TemporaryPost temporaryPost = temporaryPostWriteDTO.toTemporaryPost();
		temporaryPostRepository.save(temporaryPost);
		postRepository.updateTempPost(post.getId(), temporaryPost);
	}

	private void writeNewTemporary(TemporaryPostWriteDTO temporaryPostWriteDTO) {

		Long memberId = temporaryPostWriteDTO.getMemberId();

		checkCount(memberId);
		Member member = getMember(memberId);
		postRepository.save(temporaryPostWriteDTO.toPost(member, postRepository.getCreatedDate(temporaryPostWriteDTO.getId())));
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
		lookRepository.findByPostAndMember(post, member).ifPresentOrElse(
			x -> {
			},
			() -> lookRepository.save(Look.builder()
				.post(post)
				.member(member)
				.build()
			)
		);
	}

	private void checkCount(Long memberId) {
		if (postRepository.countByMemberAndStatus(memberRepository.findById(memberId).orElseThrow(), PostStatus.TEMPORARY) >= MAX_TEMPORARY_COUNT) {
			throw new IllegalStateException("임시저장은 " + MAX_TEMPORARY_COUNT + "개까지만 가능");
		}
	}

	public Slice<LovePostDTO> getLovePosts(Long memberId, PageRequest page) {
		Slice<Post> lovePosts = postRepository.findByJoinLove(memberId, page);
		return lovePosts.map(LovePostDTO::new);
	}

	public Slice<LookPostDTO> getLookPosts(Long memberId, PageRequest page) {
		Slice<Post> lookPosts = postRepository.findByJoinLook(memberId, page);
		return lookPosts.map(LookPostDTO::new);
	}

	/**
	 * post가 series에 속해있으면 그 series에 속한 이전 post, 다음 post를 보내줘야 함. post가 series에 속해있지 않다면 단순히 이전 post, 다음 post를 보내주면 된다.
	 */
	public Page<ReadDTO> findReadPost(Long postId) {
		Post findPost = postRepository.findById(postId).orElseThrow();
		Post nextPost = postRepository.findPrevPost(findPost);
		Post prevPost = postRepository.findNextPost(findPost);
		return postRepository.findReadPost(postId).map(post -> new ReadDTO(post, prevPost, nextPost));
	}

	public void findReadPostTest(Long postId) {
		Post findPost = postRepository.findById(postId).orElseThrow();
		List<Post> post = postRepository.findPrevNextPost(findPost);
	}

	public Slice<SeriesPostSummaryDTO> findSeriesPost(Long memberId, String seriesName, PageRequest page) {
		Slice<Post> seriesPosts = postRepository.findByJoinSeries(memberId, seriesName, page);
		return seriesPosts.map(SeriesPostSummaryDTO::new);
	}
}
