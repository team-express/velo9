package teamexpress.velo9.post.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import teamexpress.velo9.post.dto.PostSaveDTO;
import teamexpress.velo9.post.service.PostService;

@RestController
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	@PostMapping(value = "/write", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> write(@RequestBody PostSaveDTO postSaveDTO) {
		return new ResponseEntity<>(postService.write(postSaveDTO), HttpStatus.OK);
	}
}
