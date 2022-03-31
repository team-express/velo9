package teamexpress.velo9.post.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
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
public class SeriesControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void getList() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/getSeriesList")
				.param("memberId", "2"))
			.andExpect(status().isOk())
			.andDo(document("seriesList"
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
					+ "\n}")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("addSeries"
			));
	}

	@Test
	@Transactional
	@Rollback
	void deleteSeries() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.post("/deleteSeries")
				.param("seriesId", "12"))
			.andExpect(status().isOk())
			.andDo(document("deleteSeries"
			));
	}
}
