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
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnailator;

import lombok.extern.slf4j.Slf4j;
import teamexpress.velo9.post.domain.PostThumbnailType;
import teamexpress.velo9.post.dto.PostThumbnailFileDTO;

@Service
@Slf4j
public class PostThumbnailFileService {
	private static final String ROOT_PATH = "c:\\resources";
	private static final String BACKSLASH = "\\";
	private static final String NAME_SEPARATOR = "_";
	private static final String THUMBNAIL_MARK = "s_";
	private static final int NEXT = 1;

	private static File getFile(String fileName) {
		return new File(ROOT_PATH + BACKSLASH + fileName);
	}

	private static String getFolder() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Date date = new Date();

		String str = sdf.format(date);

		return str.replace("-", File.separator);
	}

	private static void createFile(MultipartFile uploadFile, File uploadPath, String uploadFullFileName) {
		File saveFile = new File(uploadPath, uploadFullFileName);

		try {
			uploadFile.transferTo(saveFile);

			FileOutputStream thumbnail = new FileOutputStream(
				new File(uploadPath, THUMBNAIL_MARK + uploadFullFileName));
			Thumbnailator.createThumbnail(uploadFile.getInputStream(), thumbnail, 100, 100);
			thumbnail.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getUploadFileName(MultipartFile uploadFile) {
		String uploadFileName = uploadFile.getOriginalFilename();
		uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf(BACKSLASH) + NEXT);
		return uploadFileName;
	}

	private static File getUploadPath(String uploadFolderPath) {
		File uploadPath = new File(ROOT_PATH, uploadFolderPath);

		if (!uploadPath.exists()) {
			uploadPath.mkdirs();
		}

		return uploadPath;
	}

	private static void checkUploadFile(MultipartFile uploadFile) {
		if (uploadFile == null || uploadFile.isEmpty() || PostThumbnailType.check(uploadFile.getContentType()))
			throw new IllegalStateException();
	}

	public PostThumbnailFileDTO upload(MultipartFile uploadFile) {
		checkUploadFile(uploadFile);

		String uploadFileName = getUploadFileName(uploadFile);

		String uploadFolderPath = getFolder();

		File uploadPath = getUploadPath(uploadFolderPath);

		String uuid = UUID.randomUUID().toString();

		PostThumbnailFileDTO postThumbnailFileDTO = new PostThumbnailFileDTO(uuid, uploadFileName, uploadFolderPath);

		createFile(uploadFile, uploadPath, uuid + NAME_SEPARATOR + uploadFileName);

		return postThumbnailFileDTO;
	}

	private static void checkDeleteFileName(String fileName) {
		if (fileName == null || fileName.equals(""))
			throw new IllegalStateException();
	}

	public void deleteFile(String fileName) {
		checkDeleteFileName(fileName);

		File file;

		try {
			file = new File(ROOT_PATH + BACKSLASH + URLDecoder.decode(fileName, "UTF-8"));

			file.delete();

			String largeFileName = file.getAbsolutePath().replace(THUMBNAIL_MARK, "");

			file = new File(largeFileName);

			file.delete();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public byte[] getImage(String fileName) {

		byte[] result = null;

		try {
			result = FileCopyUtils.copyToByteArray(getFile(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

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
