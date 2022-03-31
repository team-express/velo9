package teamexpress.velo9.post.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc // -> webAppContextSetup(webApplicationContext)
@AutoConfigureRestDocs // -> apply(documentationConfiguration(restDocumentation))
@SpringBootTest
class PostControllerTest {

	@Autowired
	private MockMvc mockMvc;


	@Test
	void getlikeArchive() throws Exception {

		mockMvc.perform(get("/archive/like")
				.param("memberId", "2"))
			.andExpect(status().isOk())
			.andDo(document("likeArchive",
				responseFields(
					fieldWithPath("content[].id").description("memberId를 가져온다"),
					fieldWithPath("content[].title").description("title을 가져온다"),
					fieldWithPath("content[].introduce").description("introduce를 가져온다"),
					fieldWithPath("content[].createDate").description("createDate를 가져온다")
				)
			));
	}
}
