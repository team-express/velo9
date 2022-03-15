package teamexpress.velo9.member.api;

import java.net.MalformedURLException;
import java.net.URL;
import org.springframework.stereotype.Component;
import teamexpress.velo9.post.dto.PostThumbnailFileDTO;

@Component
public class MemberThumbnailFileUploader {

	public PostThumbnailFileDTO upload(String urlStr){

		try {
			URL url = new URL(urlStr);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	return null;
	}
}
