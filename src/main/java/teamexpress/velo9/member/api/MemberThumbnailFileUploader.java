package teamexpress.velo9.member.api;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import javax.imageio.ImageIO;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.stereotype.Component;
import teamexpress.velo9.member.dto.MemberThumbnailDTO;

@Component
public class MemberThumbnailFileUploader {

	private static final String ROOT_PATH = "C:\\member";
	private static final String BACKSLASH = "\\";
	private static final String NAME_SEPARATOR = "_";
	private static final String THUMBNAIL_MARK = "s_";
	private static final String uploadFileName = "default.png";

	private String getFolder() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Date date = new Date();

		String str = sdf.format(date);

		return str.replace("-", File.separator);
	}

	private File getUploadPath(String uploadFolderPath) {
		File uploadPath = new File(ROOT_PATH, uploadFolderPath);

		if (!uploadPath.exists()) {
			uploadPath.mkdirs();
		}

		return uploadPath;
	}

	public MemberThumbnailDTO upload(String urlStr) {
		MemberThumbnailDTO memberThumbnailDTO = null;

		try {
			URL url = new URL(urlStr);
			BufferedImage img = ImageIO.read(url);

			String uploadFolderPath = getFolder();
			File uploadPath = getUploadPath(uploadFolderPath);
			String uuid = UUID.randomUUID().toString();

			memberThumbnailDTO = new MemberThumbnailDTO(uuid,
				uploadFileName,
				uploadFolderPath);

			String uploadFullFileName = uuid + NAME_SEPARATOR + uploadFileName;

			File file = new File(uploadPath, uploadFullFileName);
			ImageIO.write(img, "png", file);

			File thumbnail = new File(uploadPath, THUMBNAIL_MARK + uploadFullFileName);

			Thumbnailator.createThumbnail(file, thumbnail, img.getWidth(), img.getHeight());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return memberThumbnailDTO;
	}
}
