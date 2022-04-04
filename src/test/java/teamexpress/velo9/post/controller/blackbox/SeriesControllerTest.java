package teamexpress.velo9.post.controller.blackbox;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import teamexpress.velo9.config.RestDocsConfig;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
@Import(RestDocsConfig.class)
public class SeriesControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("시리즈 삭제 시 포스트에 setNull 해야해")
	@Transactional
	@Rollback
	void postCascade() throws Exception {
		mockMvc.perform(post("/deleteSeries")
				.param("seriesId", "1"))
			.andExpect(status().isOk())
			.andDo(document("PostDeleteSeries",
				requestParameters(
					parameterWithName("seriesId").description("삭제할 시리즈의 id")
				)
			));
	}
}
