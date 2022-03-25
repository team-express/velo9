package teamexpress.velo9.post.controller;

import lombok.RequiredArgsConstructor;
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
import teamexpress.velo9.member.domain.Look;
import teamexpress.velo9.member.security.oauth.SessionConst;
import teamexpress.velo9.post.dto.*;
import teamexpress.velo9.post.service.PostService;
import teamexpress.velo9.post.service.TagService;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

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

	@GetMapping("/{nickname}/series")
	public ResponseEntity<Slice<SeriesDTO>> series(
		@PathVariable String nickname,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "5") int size) {

		PageRequest pageRequest = PageRequest.of(page, size);

		Slice<SeriesDTO> series = postService.findSeries(nickname, pageRequest);
		return new ResponseEntity<>(series, HttpStatus.OK);
	}

	@GetMapping("/{nickname}/main")
	public ResponseEntity<Slice<PostReadDTO>> postsRead(@PathVariable String nickname,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size) {

		PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Direction.DESC, "createdDate"));

		Slice<PostReadDTO> post = postService.findReadPost(nickname, pageRequest);
		return new ResponseEntity<>(post, HttpStatus.OK);
	}

	@GetMapping("/temp")
	public ResponseEntity<Result<List<TempSavedPostDTO>>> tempPostsRead(HttpSession session) {
		Long memberId = getMemberId(session);
		return new ResponseEntity<>(new Result(postService.getTempSavedPost(memberId)), HttpStatus.OK);
	}

	@PostMapping("/love")
	public void love(@RequestBody LoveDTO loveDTO) {
		postService.loveOrNot(loveDTO);
	}

	@PostMapping("/look")//차후 상세보기가 생기면 녹아들어야 할 로직
	public void look(@RequestBody LookDTO lookDTO) {
		postService.look(lookDTO);
	}

	@GetMapping("/archive/like")
	public ResponseEntity<Slice<LovePostDTO>> lovePostRead(HttpSession session,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "20") int size) {

		PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Direction.DESC, "createdDate"));

		Slice<LovePostDTO> lovePosts = postService.getLovePosts(getMemberId(session), pageRequest);
		return new ResponseEntity<>(lovePosts, HttpStatus.OK);
	}

	@GetMapping("/archive/recent")
	public ResponseEntity<Slice<LookPostDTO>> lookPostRead(HttpSession session,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "20") int size) {

		PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Direction.DESC, "createdDate"));

		Slice<LookPostDTO> lookPosts = postService.getLookPosts(getMemberId(session), pageRequest);
		return new ResponseEntity<>(lookPosts, HttpStatus.OK);
	}

	private Long getMemberId(HttpSession session) {
		return (Long) session.getAttribute(SessionConst.LOGIN_MEMBER);
	}
}
