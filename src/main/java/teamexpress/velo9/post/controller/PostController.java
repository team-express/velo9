package teamexpress.velo9.post.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import teamexpress.velo9.post.dto.PostDTO;
import teamexpress.velo9.post.service.PostService;

@RestController
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	@GetMapping("/write")
	public ResponseEntity<String> writeGet() {

		return new ResponseEntity<>("test", HttpStatus.OK);
	}

	@PostMapping(value = "/write", consumes = "application/json")
	public ResponseEntity<Long> writePost(@RequestBody PostDTO postDTO) {

		return new ResponseEntity<>(postService.save(postDTO), HttpStatus.OK);
	}
}
