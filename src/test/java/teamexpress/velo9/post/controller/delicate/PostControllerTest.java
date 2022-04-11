package teamexpress.velo9.post.controller.delicate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Log4j2
public class PostControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private EntityManager em;

	//writeGET
	@Test
	void id에_null이_들어왔을_경우_새글작성이므로_null을_반환한다() throws Exception {
		mockMvc.perform(get("/write"))
			.andExpect(status().isOk())
			.andExpect(content().string(""));
	}

	@Test
	void 없는_게시글의_id가_들어왔을_경우_예외를_던진다() {
		//가장 큰 id보다
		Query query = em.createNativeQuery("select MAX(post_id) from post");
		Long postId = ((BigInteger) query.getSingleResult()).longValue();

		assertThatThrownBy(() ->
			mockMvc.perform(get("/write")
					.param("id", String.valueOf(postId + 1)))//1큰 id는 없는 게시물이다.
				.andExpect(status().isOk())).isInstanceOf(Exception.class);
	}//end
}
