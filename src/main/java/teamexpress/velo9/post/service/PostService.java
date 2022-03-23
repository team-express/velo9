package teamexpress.velo9.post.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import teamexpress.velo9.post.domain.PostThumbnail;
import teamexpress.velo9.post.domain.PostThumbnailRepository;
import teamexpress.velo9.post.domain.Series;
import teamexpress.velo9.post.domain.SeriesRepository;
import teamexpress.velo9.post.dto.LookDTO;
import teamexpress.velo9.post.dto.LoveDTO;
import teamexpress.velo9.post.dto.PostMainDTO;
import teamexpress.velo9.post.dto.PostReadDTO;
import teamexpress.velo9.post.dto.PostSaveDTO;
import teamexpress.velo9.post.dto.PostThumbnailDTO;
import teamexpress.velo9.post.dto.SearchCondition;
import teamexpress.velo9.post.dto.SeriesDTO;
import teamexpress.velo9.post.domain.*;
import teamexpress.velo9.post.dto.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

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

	public Page<PostMainDTO> getMainPage(Pageable pageable) {
		Page<Post> mainPage = postRepository.findMainPage(pageable);
		return mainPage.map(PostMainDTO::new);
	}

	public Page<PostMainDTO> searchMain(SearchCondition searchCondition, Pageable pageable) {

		if (searchCondition.isTagSelect()) {
			Page<Post> posts = postRepository.searchTag(pageable);

			List<PostMainDTO> collect = posts.stream().filter(post -> post.getPostTags().stream()
					.anyMatch(postTag -> postTag.getTag().getName().equals(searchCondition.getContent())))
				.map(PostMainDTO::new).collect(Collectors.toList());
			return new PageImpl<>(collect);

		}
		return postRepository.search(searchCondition, pageable)
			.map(PostMainDTO::new);
	}

	public List<TempSavedPostDTO> getTempSavedPost(Long id) {

		List<Post> findPosts = postRepository.getTempSavedPost(id, PostStatus.TEMPORARY);

		return findPosts.stream()
			.map(p -> new TempSavedPostDTO(p))
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
}
