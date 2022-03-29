package teamexpress.velo9.post.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import teamexpress.velo9.common.domain.Result;
import teamexpress.velo9.member.security.oauth.SessionConst;
import teamexpress.velo9.post.dto.LookPostDTO;
import teamexpress.velo9.post.dto.LoveDTO;
import teamexpress.velo9.post.dto.LovePostDTO;
import teamexpress.velo9.post.dto.PostReadDTO;
import teamexpress.velo9.post.dto.PostSaveDTO;
import teamexpress.velo9.post.dto.ReadDTO;
import teamexpress.velo9.post.dto.SeriesDTO;
import teamexpress.velo9.post.dto.SeriesPostSummaryDTO;
import teamexpress.velo9.post.dto.TempSavedPostDTO;
import teamexpress.velo9.post.dto.TemporaryPostWriteDTO;
import teamexpress.velo9.post.service.PostService;
import teamexpress.velo9.post.service.TagService;

@RestController
@RequiredArgsConstructor
public class PostController {

	private static final int SERIES_SIZE = 5;
	private static final int SIZE = 10;
	private static final int ARCHIVE_SIZE = 20;

	private final PostService postService;
	private final TagService tagService;

	@GetMapping("/write")
	public ResponseEntity<PostSaveDTO> write(@RequestParam("postId") Long postId) {
		return new ResponseEntity<>(postService.getPostById(postId), HttpStatus.OK);
	}

	@PostMapping("/write")
	public ResponseEntity<Long> write(@RequestBody PostSaveDTO postSaveDTO) {
		Long postId = postService.write(postSaveDTO);
		tagService.addTags(postId, postSaveDTO.getTagNames());
		tagService.removeUselessTags();

		return new ResponseEntity<>(postId, HttpStatus.OK);
	}

	@PostMapping("/writeTemporary")
	public void writeTemporary(@RequestBody TemporaryPostWriteDTO temporaryPostWriteDTO) {
		postService.writeTemporary(temporaryPostWriteDTO);
	}

	@PostMapping("/delete")
	public void delete(@RequestParam("postId") Long id) {
		postService.delete(id);
	}

	@GetMapping("/{nickname}/series")
	public ResponseEntity<Slice<SeriesDTO>> series(
		@PathVariable String nickname,
		@RequestParam(defaultValue = "0") int page) {

		PageRequest pageRequest = PageRequest.of(page, SERIES_SIZE);

		Slice<SeriesDTO> series = postService.findSeries(nickname, pageRequest);
		return new ResponseEntity<>(series, HttpStatus.OK);
	}

	@GetMapping("/{nickname}/series/{seriesName}")
	public ResponseEntity<Slice<SeriesPostSummaryDTO>> seriesPost(
		@PathVariable String seriesName,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "descending") String sortCondition,
		@RequestParam Long memberId,
		HttpSession session) {

		PageRequest pageRequest = getPageRequest(page, sortCondition);

		Slice<SeriesPostSummaryDTO> seriesPost = postService.findSeriesPost(memberId, seriesName, pageRequest);
		return new ResponseEntity<>(seriesPost, HttpStatus.OK);
	}

	@GetMapping("/{nickname}/main")
	public ResponseEntity<Slice<PostReadDTO>> postsRead(@PathVariable String nickname,
		@RequestParam(defaultValue = "0") int page) {

		PageRequest pageRequest = PageRequest.of(page, SIZE, Sort.by(Direction.DESC, "createdDate"));

		Slice<PostReadDTO> post = postService.findPost(nickname, pageRequest);
		return new ResponseEntity<>(post, HttpStatus.OK);
	}

	@GetMapping("/temp")
	public ResponseEntity<Result<List<TempSavedPostDTO>>> tempPostsRead(@RequestParam Long memberId, HttpSession session) {
		return new ResponseEntity<>(new Result(postService.getTempSavedPost(memberId)), HttpStatus.OK);
	}

	@PostMapping("/love")
	public void love(@RequestBody LoveDTO loveDTO) {
		postService.loveOrNot(loveDTO);
	}

	@GetMapping("/archive/like")
	public ResponseEntity<Slice<LovePostDTO>> lovePostRead(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam Long memberId) {

		PageRequest pageRequest = PageRequest.of(page, ARCHIVE_SIZE, Sort.by(Direction.DESC, "createdDate"));

		Slice<LovePostDTO> lovePosts = postService.getLovePosts(memberId, pageRequest);
		return new ResponseEntity<>(lovePosts, HttpStatus.OK);
	}

	@GetMapping("/archive/recent")
	public ResponseEntity<Slice<LookPostDTO>> lookPostRead(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam Long memberId) {

		PageRequest pageRequest = PageRequest.of(page, ARCHIVE_SIZE, Sort.by(Direction.DESC, "createdDate"));

		Slice<LookPostDTO> lookPosts = postService.getLookPosts(memberId, pageRequest);
		return new ResponseEntity<>(lookPosts, HttpStatus.OK);
	}

	@GetMapping("/{nickname}/read/{postId}")
	public ResponseEntity<Page<ReadDTO>> readPost(@PathVariable Long postId, @RequestParam Long memberId) {
		Page<ReadDTO> content = postService.findReadPost(postId);
		postService.look(postId, memberId);
		return new ResponseEntity<>(content, HttpStatus.OK);
//		postService.findReadPostTest(postId);
	}

	private PageRequest getPageRequest(int page, String sortValue) {
		Sort sort = sortValue.equals(("old")) ?
			Sort.by(Direction.ASC, "createdDate") : Sort.by(Direction.DESC, sortValue);

		return PageRequest.of(page, SIZE, sort);
	}
}
