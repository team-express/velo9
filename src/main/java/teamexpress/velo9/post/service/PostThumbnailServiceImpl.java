package teamexpress.velo9.post.service;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnailator;

import lombok.RequiredArgsConstructor;
import teamexpress.velo9.post.domain.Post;
import teamexpress.velo9.post.domain.PostRepository;
import teamexpress.velo9.post.domain.PostThumbnail;
import teamexpress.velo9.post.domain.PostThumbnailRepository;
import teamexpress.velo9.post.dto.PostThumbnailDTO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostThumbnailServiceImpl implements PostThumbnailService {
	private static final String ROOT_PATH = "c:\\resources";
	private static final String BACKSLASH = "\\";

	private final PostThumbnailRepository postThumbnailRepository;
	private final PostRepository postRepository;

	private static File getFile(String fileName) {
		return new File(ROOT_PATH + BACKSLASH + fileName);
	}

	private static String getFolder() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Date date = new Date();

		String str = sdf.format(date);

		return str.replace("-", File.separator);
	}

	private static void createThumbnail(MultipartFile uploadFile, File uploadPath, String uploadFullFileName) {
		try {
			FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFullFileName));
			Thumbnailator.createThumbnail(uploadFile.getInputStream(), thumbnail, 100, 100);
			thumbnail.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getUploadFileName(MultipartFile uploadFile) {
		String uploadFileName = uploadFile.getOriginalFilename();
		uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf(BACKSLASH) + 1);
		return uploadFileName;
	}

	private static File getUploadPath(String uploadFolderPath) {
		File uploadPath = new File(ROOT_PATH, uploadFolderPath);

		if (!uploadPath.exists()) {
			uploadPath.mkdirs();
		}

		return uploadPath;
	}

	private static void transfer(MultipartFile uploadFile, File uploadPath, String uploadFullFileName) {
		File saveFile = new File(uploadPath, uploadFullFileName);

		try {
			uploadFile.transferTo(saveFile);//uploadFile, uploadPath, uploadFileName
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional
	public void register(PostThumbnailDTO postThumbnailDTO) {
		Post post = postRepository.findById(postThumbnailDTO.getPostId()).orElse(null);

		PostThumbnail postThumbnail = dtoToEntity(postThumbnailDTO, post);

		postThumbnailRepository.save(postThumbnail);
	}

	private String getUploadFullFileName(String uuid, String uploadFileName) {
		return uuid + "_" + uploadFileName;
	}

	@Override
	public PostThumbnailDTO upload(MultipartFile uploadFile) {
		String uploadFolderPath = getFolder();

		File uploadPath = getUploadPath(uploadFolderPath);

		String uploadFileName = getUploadFileName(uploadFile);

		UUID uuid = UUID.randomUUID();

		PostThumbnailDTO postThumbnailDTO = PostThumbnailDTO.builder()
			.name(uploadFileName)
			.uuid(uuid.toString())
			.path(uploadFolderPath)
			.build();

		String uploadFullFileName = getUploadFullFileName(uuid.toString(), uploadFileName);

		transfer(uploadFile, uploadPath, uploadFullFileName);

		createThumbnail(uploadFile, uploadPath, uploadFullFileName);

		return postThumbnailDTO;
	}

	@Override
	@Transactional
	public void delete(Long postId) {
		postThumbnailRepository.deleteByPostId(postId);
	}

	@Override
	public void deleteFile(String fileName) {
		File file;

		try {
			file = new File(ROOT_PATH + BACKSLASH + URLDecoder.decode(fileName, "UTF-8"));

			file.delete();

			String largeFileName = file.getAbsolutePath().replace("s_", "");

			file = new File(largeFileName);

			file.delete();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public PostThumbnailDTO get(Long postId) {

		PostThumbnail postThumbnail = postThumbnailRepository.findByPostId(postId);

		PostThumbnailDTO postThumbnailDTO = new PostThumbnailDTO();

		postThumbnailDTO.setName(postThumbnail.getName());
		postThumbnailDTO.setPath(postThumbnail.getPath());
		postThumbnailDTO.setUuid(postThumbnail.getUuid());

		return postThumbnailDTO;
	}

	@Override
	public byte[] getImage(String fileName) {

		byte[] result = null;

		try {
			result = FileCopyUtils.copyToByteArray(getFile(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public HttpHeaders getHeader(String fileName) {
		HttpHeaders header = new HttpHeaders();

		try {
			header.add("Content-Type", Files.probeContentType(getFile(fileName).toPath()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return header;
	}
}
