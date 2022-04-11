package teamexpress.velo9.post.controller.delicate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigInteger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import teamexpress.velo9.member.security.oauth.SessionConst;

@SpringBootTest
@AutoConfigureMockMvc
@Log4j2
public class PostControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private EntityManager em;

	@Test
	void id에_null이_들어왔을_경우_새글작성이므로_null을_반환한다() throws Exception {
		mockMvc.perform(get("/write"))
			.andExpect(status().isOk())
			.andExpect(content().string(""));
	}

	@Test
	void 없는_게시글의_id가_들어왔을_경우_예외를_던진다() {
		Query query = em.createNativeQuery("select MAX(post_id) from post");
		Long postId = ((BigInteger) query.getSingleResult()).longValue();

		assertThatThrownBy(() ->
			mockMvc.perform(get("/write")
					.param("id", String.valueOf(postId + 1)))
				.andExpect(status().isOk())).isInstanceOf(Exception.class);
	}

	@Test
	void writePost() throws Exception {
		mockMvc.perform(post("/write")
				.sessionAttr(SessionConst.LOGIN_MEMBER, 2l)
				.content("{"
					+ "\n\"title\":\"testtest\","
					+ "\n\"content\":\"333333\""
					+ "\n}")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());

		mockMvc.perform(post("/write")
				.sessionAttr(SessionConst.LOGIN_MEMBER, 2l)
				.content("{"
					+ "\n\"postId\":7,"
					+ "\n\"title\":\"수정\","
					+ "\n\"content\":\"수정\""
					+ "\n}")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
}
