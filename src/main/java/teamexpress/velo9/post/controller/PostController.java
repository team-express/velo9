package teamexpress.velo9.post.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import teamexpress.velo9.post.dto.PostSaveDTO;
import teamexpress.velo9.post.service.PostService;
import teamexpress.velo9.post.service.TagService;

@RestController
@RequiredArgsConstructor
@Log4j2
public class PostController {

	private final PostService postService;
	private final TagService tagService;

	@PostMapping(value = "/write", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> write(@RequestBody PostSaveDTO postSaveDTO) {

		Long postId = postService.write(postSaveDTO);
		tagService.addTags(postId, postSaveDTO.getTagNames());

		return new ResponseEntity<>(postId, HttpStatus.OK);
	}
}
