package teamexpress.velo9.post.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import teamexpress.velo9.post.dto.PostThumbnailDTO;
import teamexpress.velo9.post.service.PostThumbnailService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PostThumbnailController {

	private final PostThumbnailService postThumbnailService;

	@GetMapping("/getPostThumbnail")
	public ResponseEntity<PostThumbnailDTO> get(Long postId) {
		return new ResponseEntity<>(postThumbnailService.get(postId), HttpStatus.OK);
	}

	@GetMapping("/displayPostThumbnail")
	public ResponseEntity<byte[]> display(String fileName) {
		return new ResponseEntity<>(postThumbnailService.getImage(fileName), postThumbnailService.getHeader(fileName),
			HttpStatus.OK);
	}

	@PostMapping("/uploadPostThumbnail")
	public ResponseEntity<PostThumbnailDTO> upload(MultipartFile uploadFile, Long postId) {

		PostThumbnailDTO postThumbnailDTO = postThumbnailService.upload(uploadFile);
		postThumbnailDTO.setPostId(postId);
		postThumbnailService.register(postThumbnailDTO);

		return new ResponseEntity<>(postThumbnailDTO, HttpStatus.OK);
	}

	@PostMapping("/deletePostThumbnail")
	public ResponseEntity<String> delete(String fileName, Long postId) {

		postThumbnailService.deleteFile(fileName);
		postThumbnailService.delete(postId);

		return new ResponseEntity<>("deleted", HttpStatus.OK);
	}
}
