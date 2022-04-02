package teamexpress.velo9.post.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedResponseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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
	void getList() throws Exception {

		mockMvc.perform(get("/getSeriesList")
				.param("memberId", "2"))
			.andExpect(status().isOk())
			.andDo(document("seriesList",
				requestParameters(
					parameterWithName("memberId").description("현재 글 작성중인 회원의 id 입니다.")
				),
				relaxedResponseFields(
					fieldWithPath("data").description("시리즈의 id와 이름이 담긴 정보들이 있습니다.")
				)
			));
	}

	@Test
	@Transactional
	@Rollback
	void addSeries() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.post("/addSeries")
				.content("{"
					+ "\n\"memberId\":1,"
					+ "\n\"name\":\"loveyou\""
					+ "\n}"))
			.andExpect(status().isOk())
			.andDo(document("addSeries",
				requestFields(
					fieldWithPath("memberId").description("현재 글 작성중인 회원의 id"),
					fieldWithPath("name").description("원하는 새 시리즈 이름 입력, 단 내부적으로 중복검사 합니다.")
				)
			));
	}

	@Test
	@Transactional
	@Rollback
	void deleteSeries() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.post("/deleteSeries")
				.param("seriesId", "10"))
			.andExpect(status().isOk())
			.andDo(document("deleteSeries",
				requestParameters(
					parameterWithName("seriesId").description("삭제할 시리즈의 id")
				)
			));
	}
}
