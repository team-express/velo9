package teamexpress.velo9.post.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import teamexpress.velo9.post.dto.PostThumbnailFileDTO;
import teamexpress.velo9.post.service.PostThumbnailFileService;

@RestController
@RequiredArgsConstructor
public class PostThumbnailFileController {

	private final PostThumbnailFileService postThumbnailFileService;

	@GetMapping("/displayPostThumbnail")
	public ResponseEntity<byte[]> display(String fileName) {
		return new ResponseEntity<>(postThumbnailFileService.getImage(fileName),
			postThumbnailFileService.getHeader(fileName),
			HttpStatus.OK);
	}

	@PostMapping("/uploadPostThumbnail")
	public ResponseEntity<PostThumbnailFileDTO> upload(MultipartFile uploadFile) {

		PostThumbnailFileDTO postThumbnailFileDTO = postThumbnailFileService.upload(uploadFile);

		return new ResponseEntity<>(postThumbnailFileDTO, HttpStatus.OK);
	}

	@PostMapping("/deletePostThumbnail")
	public ResponseEntity<String> delete(String fileName) {

		postThumbnailFileService.deleteFile(fileName);

		return new ResponseEntity<>("deleted", HttpStatus.OK);
	}
}
