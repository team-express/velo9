package teamexpress.velo9.post.controller;

import lombok.AllArgsConstructor;
import teamexpress.velo9.post.service.PostThumbnailService;

//@RestController
@AllArgsConstructor
public class PostThumbnailController {

	private PostThumbnailService postThumbnailService;

	// @GetMapping("/get") // attachVO를 불러온다.
	// public ResponseEntity<PostThumbnail> getA(Long post_id) {
	// 	return new ResponseEntity<>(postThumbnailService.get(post_id), HttpStatus.OK);
	// }
}
