package teamexpress.velo9;

import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Testt {

	@Test
	public void test(){
		try {
			URL url = new URL("https://lh3.googleusercontent.com/a/AATXAJwifNUqhy1f-gqxKpLL5_Jqigb6_YPmEAgdDbMS=s96-c");
			BufferedImage img = ImageIO.read(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
