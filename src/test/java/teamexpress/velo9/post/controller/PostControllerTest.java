package teamexpress.velo9.post.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
public class PostControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void writeGet() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/write")
				.param("postId", "7"))
			.andExpect(status().isOk())
			.andDo(document("writeGet",
				responseFields(
					fieldWithPath("id").description("memberId"),
					fieldWithPath("title").description(""),
					fieldWithPath("introduce").description("없으면 백에서 content 일부 떼와서 넣어줍니다").optional(),
					fieldWithPath("content").description(""),
					fieldWithPath("access").description("PUBLIC, PRIVATE 중에서 적습니다, 없으면 PUBLIC입니다.").optional(),
					fieldWithPath("memberId").description("곧 없어질 예정"),
					fieldWithPath("seriesId").description("memberId"),
					fieldWithPath("tagNames"),
					fieldWithPath("postThumbnailDTO"),
					fieldWithPath("postThumbnailDTO.uuid"),
					fieldWithPath("postThumbnailDTO.path"),
					fieldWithPath("postThumbnailDTO.name"),
					fieldWithPath("temporaryPostReadDTO").description("2")
				)
			));
	}

	@Test
	void writePost() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.post("/write")
				.content("{"
					+ "\n\"id\":1,"
					+ "\n\"title\":\"testtest\","
					+ "\n\"introduce\":\"1\","
					+ "\n\"content\":\"333333\","
					+ "\n\"access\":\"PRIVATE\","
					+ "\n\"memberId\":1,"
					+ "\n\"seriesId\":1,"
					+ "\n\"tagNames\":null,"
					+ "\n\"postThumbnailDTO\":null"
					+ "\n}")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("writePost",
				requestFields(
					fieldWithPath("id").description("memberId").optional(),
					fieldWithPath("title").description(""),
					fieldWithPath("introduce").description("없으면 백에서 content 일부 떼와서 넣어줍니다").optional(),
					fieldWithPath("content").description(""),
					fieldWithPath("access").description("PUBLIC, PRIVATE 중에서 적습니다, 없으면 PUBLIC입니다.").optional(),
					fieldWithPath("memberId").description("곧 없어질 예정"),
					fieldWithPath("seriesId").description("memberId"),
					fieldWithPath("tagNames").description("[]로 주세요").optional(),
					fieldWithPath("postThumbnailDTO").description("필드 3개(uuid, path, name)를 가지고 있는 어떤 객체가 있습니다\n"
						+ "그 놈을 지니고 있다가 주세요").optional()
				)
			));
	}
	@Test
	void ssss() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.post("/signup")
				.content("{"
					+ "\n\"username\":\"testtest\","
					+ "\n\"password\":\"1\","
					+ "\n\"nickname\":\"333333\","
					+ "\n\"blogTitle\":\"PRIVATE\","
					+ "\n\"email\":\"tesasdf@nate.com\""
					+ "\n}")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("writePost"
//				requestFields(
//					fieldWithPath("id").description("memberId").optional(),
//					fieldWithPath("title").description(""),
//					fieldWithPath("introduce").description("없으면 백에서 content 일부 떼와서 넣어줍니다").optional(),
//					fieldWithPath("content").description(""),
//					fieldWithPath("access").description("PUBLIC, PRIVATE 중에서 적습니다, 없으면 PUBLIC입니다.").optional(),
//					fieldWithPath("memberId").description("곧 없어질 예정"),
//					fieldWithPath("seriesId").description("memberId"),
//					fieldWithPath("tagNames").description("[]로 주세요").optional(),
//					fieldWithPath("postThumbnailDTO").description("필드 3개(uuid, path, name)를 가지고 있는 어떤 객체가 있습니다\n"
//						+ "그 놈을 지니고 있다가 주세요").optional()
//				)
			));
	}
}
