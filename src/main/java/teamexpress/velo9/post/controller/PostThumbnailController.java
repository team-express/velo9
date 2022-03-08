package teamexpress.velo9.post.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnailator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import teamexpress.velo9.post.dto.PostThumbnailDTO;
import teamexpress.velo9.post.service.PostThumbnailService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PostThumbnailController {
	private static final String ROOT_PATH = "c:\\resources";
	private static final String BACKSLASH = "\\";

	private final PostThumbnailService postThumbnailService;

	//얻어오기
	@GetMapping("/getPostThumbnail")
	public ResponseEntity<PostThumbnailDTO> get(Long post_id) {
		return new ResponseEntity<>(postThumbnailService.get(post_id), HttpStatus.OK);
	}

	//보여주기
	@GetMapping("/displayPostThumbnail")
	public ResponseEntity<byte[]> display(String fileName) {

		File file = new File(ROOT_PATH + BACKSLASH + fileName);

		ResponseEntity<byte[]> result = null;

		try {
			HttpHeaders header = new HttpHeaders();

			header.add("Content-Type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	//등록
	@PostMapping("/uploadPostThumbnail")
	public ResponseEntity<PostThumbnailDTO> upload(MultipartFile uploadFile, Long post_id) {

		PostThumbnailDTO postThumbnailDTO = new PostThumbnailDTO();

		String uploadFolder = ROOT_PATH;

		String uploadFolderPath = getFolder();

		File uploadPath = new File(uploadFolder, uploadFolderPath);

		if (!uploadPath.exists()) {
			uploadPath.mkdirs();
		}

		String uploadFileName = uploadFile.getOriginalFilename();

		uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf(BACKSLASH) + 1);

		UUID uuid = UUID.randomUUID();
		postThumbnailDTO.setName(uploadFileName);
		uploadFileName = uuid + "_" + uploadFileName;

		File saveFile = new File(uploadPath, uploadFileName);

		try {
			uploadFile.transferTo(saveFile);

			postThumbnailDTO.setPost_id(post_id);
			postThumbnailDTO.setUuid(uuid.toString());
			postThumbnailDTO.setPath(uploadFolderPath);

			if (checkImageType(saveFile)) {
				FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));
				Thumbnailator.createThumbnail(uploadFile.getInputStream(), thumbnail, 100, 100);
				thumbnail.close();
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		postThumbnailService.register(postThumbnailDTO);

		return new ResponseEntity<>(postThumbnailDTO, HttpStatus.OK);
	}

	//삭제
	@PostMapping("/deletePostThumbnail")
	public ResponseEntity<String> delete(String fileName, Long post_id) {

		File file;

		try {
			file = new File(ROOT_PATH + BACKSLASH + URLDecoder.decode(fileName, "UTF-8"));

			file.delete();

			String largeFileName = file.getAbsolutePath().replace("s_", "");

			file = new File(largeFileName);

			file.delete();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		postThumbnailService.delete(post_id);

		return new ResponseEntity<>("deleted", HttpStatus.OK);
	}

	private String getFolder() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Date date = new Date();

		String str = sdf.format(date);

		return str.replace("-", File.separator);
	}

	private boolean checkImageType(File file) {

		try {
			String contentType = Files.probeContentType(file.toPath());

			return contentType.startsWith("image");

		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}
}
