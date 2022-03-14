package teamexpress.velo9.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import teamexpress.velo9.post.dto.PostThumbnailSaveDTO;
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
	public ResponseEntity<PostThumbnailSaveDTO> upload(MultipartFile uploadFile) {

		PostThumbnailSaveDTO postThumbnailSaveDTO = postThumbnailFileService.upload(uploadFile);

		return new ResponseEntity<>(postThumbnailSaveDTO, HttpStatus.OK);
	}

	@PostMapping("/deletePostThumbnail")
	public ResponseEntity<String> delete(String fileName) {

		postThumbnailFileService.deleteFile(fileName);

		return new ResponseEntity<>("deleted", HttpStatus.OK);
	}
}
