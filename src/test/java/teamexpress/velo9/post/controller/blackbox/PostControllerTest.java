package teamexpress.velo9.post.controller.blackbox;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import teamexpress.velo9.config.RestDocsConfig;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
@Import(RestDocsConfig.class)
public class PostControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("중복된 태그가 들어오면")
	void duplicatedTags() {

	}

	@Test
	@DisplayName("post삭제되면 좋아요와 보기도 같이 삭제되어야 한다")
	void loveAndLookCascade() {

	}
}
