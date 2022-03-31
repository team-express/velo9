package teamexpress.velo9.post.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

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
					fieldWithPath("tagNames").description("memberId"),
					fieldWithPath("postThumbnailDTO.uuid").description("memberId"),
					fieldWithPath("postThumbnailDTO.path").description("memberId"),
					fieldWithPath("postThumbnailDTO.name").description("memberId"),
					fieldWithPath("temporaryPostReadDTO").description("2")
				)
			));
	}

	@Test
	void writePost() throws Exception {

		//멀티맵 써보기

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
					fieldWithPath("id").description("해당 필드가 없으면(null) 새 글(또는 임시글)이 작성됩니다.\n"
						+ "해당 필드가 있으면 기존에 존재하는 글(또는 임시글)의 수정(덮어쓰기)가 됩니다.").optional(),
					fieldWithPath("title").description(""),
					fieldWithPath("introduce").description("없으면 백엔드에서 알아서 content 일부 떼와서 넣어줍니다.").optional(),
					fieldWithPath("content").description(""),
					fieldWithPath("access").description("PUBLIC(전체공개), PRIVATE(비공개) 중에서 적습니다(물론, 없으면 PUBLIC입니다).").optional(),
					fieldWithPath("memberId").description("세션에서 로그인 된 회원의 정수값을 꺼내서 주시길 바랍니다.\n"),
					fieldWithPath("seriesId").description("상세조건에서 시리즈를 선택하면 선택된 시리즈Object내부의 id값을 의미합니다."),
					fieldWithPath("tagNames").description("태그를 입력했다면, array로([]) 주십시오").optional(),
					fieldWithPath("postThumbnailDTO").description("필드 3개(uuid, path, name)를 가지고 있는 어떤 객체가 있습니다\n"
						+ "그 놈을 지니고 있다가 주세요").optional()
				)
			));
	}

	@Test
	void writeTemporary() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.post("/writeTemporary")
				.content("{"
					+ "\n\"id\":1,"
					+ "\n\"title\":\"testTMP\","
					+ "\n\"content\":\"TMPTMP\","
					+ "\n\"memberId\":2"
					+ "\n}")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("writeTemporaryPost",
				requestFields(
					fieldWithPath("id").description("memberId").optional(),
					fieldWithPath("title").description(""),
					fieldWithPath("content").description(""),
					fieldWithPath("memberId").description("곧 없어질 예정")
				)
			));
	}

	@Test
	@Transactional
	@Rollback
	void delete() throws Exception {

		//임시로 love랑 look에 참조무결성 걸음

		mockMvc.perform(MockMvcRequestBuilders.post("/delete")
				.param("postId", "106"))
			.andExpect(status().isOk())
			.andDo(document("delete",
				requestParameters(
					parameterWithName("postId").description("")
				)
			));
	}

	@Test
	void love() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.post("/love")
				.content("{"
					+ "\n\"postId\":1,"
					+ "\n\"memberId\":2"
					+ "\n}")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("love",
				requestFields(
					fieldWithPath("postId").description("memberId").optional(),
					fieldWithPath("memberId").description("곧 없어질 예정").optional()
				)
			));
	}

}
