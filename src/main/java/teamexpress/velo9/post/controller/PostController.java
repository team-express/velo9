package teamexpress.velo9.post.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import teamexpress.velo9.post.dto.PostDTO;
import teamexpress.velo9.post.service.PostService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PostController {

	private final PostService postService;

	@PostMapping("/write")
	public ResponseEntity<Long> write(@RequestBody PostDTO postDTO) {
		return new ResponseEntity<>(postService.write(postDTO), HttpStatus.OK);
	}
}
