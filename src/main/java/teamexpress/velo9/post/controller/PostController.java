package teamexpress.velo9.post.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import teamexpress.velo9.post.dto.PostSaveDTO;
import teamexpress.velo9.post.dto.SeriesDTO;
import teamexpress.velo9.post.service.PostService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PostController {

	private final PostService postService;

	@PostMapping("/write")
	public ResponseEntity<Long> write(@RequestBody PostSaveDTO postSaveDTO) {
		return new ResponseEntity<>(postService.write(postSaveDTO), HttpStatus.OK);
	}

	@GetMapping("/{nickname}/series")
	public ResponseEntity<Slice<SeriesDTO>> series(@PathVariable String nickname, Pageable pageable) {
		Slice<SeriesDTO> series = postService.findSeries(nickname, pageable);
		return new ResponseEntity<>(series, HttpStatus.OK);
	}
}
