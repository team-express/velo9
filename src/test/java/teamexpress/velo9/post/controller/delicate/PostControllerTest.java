package teamexpress.velo9.post.controller.delicate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import teamexpress.velo9.member.security.oauth.SessionConst;
import teamexpress.velo9.post.domain.PostRepository;
import teamexpress.velo9.post.domain.PostTagRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Log4j2
public class PostControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private PostTagRepository postTagRepository;
	@Autowired
	private PostRepository postRepository;

	@Test
	void test(){
		log.info(postTagRepository.findByPost(postRepository.findById(5l).get()));
	}

	@Test
	void writeGet() throws Exception{
		mockMvc.perform(get("/write")
			.param("id","1w"))
			.andExpect(status().isOk());
	}

	@Test
	void writePost() throws Exception{
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
