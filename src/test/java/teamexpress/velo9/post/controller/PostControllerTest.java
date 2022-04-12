package teamexpress.velo9.post.controller;


import static org.assertj.core.api.Assertions.assertThat;
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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import teamexpress.velo9.member.security.oauth.SessionConst;
import teamexpress.velo9.post.domain.PostStatus;

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
		Query query = em.createNativeQuery("select MAX(post_id) from post");
		Long postId = ((BigInteger) query.getSingleResult()).longValue();

		assertThatThrownBy(() ->
			mockMvc.perform(get("/write")
					.param("id", String.valueOf(postId + 1)))
				.andExpect(status().isOk())).isInstanceOf(Exception.class);

	}

	//writePOST - 새 글 작성
	@Test
	@Transactional
	@Rollback
	void 새글작성시_제목_내용_공개설정이_비어있으면_에러가_발생한다() {

		//제목이 없는 경우
		assertThatThrownBy(() ->
			mockMvc.perform(post("/write")
					.sessionAttr(SessionConst.LOGIN_MEMBER, 2l)
					.content("{"
						+ "\n\"content\":\"333333\","
						+ "\n\"access\":\"PRIVATE\""
						+ "\n}")
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
		).isInstanceOf(Error.class);

		//내용이 없는 경우
		assertThatThrownBy(() ->
			mockMvc.perform(post("/write")
					.sessionAttr(SessionConst.LOGIN_MEMBER, 2l)
					.content("{"
						+ "\n\"title\":\"testtest\","
						+ "\n\"access\":\"PRIVATE\""
						+ "\n}")
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
		).isInstanceOf(Error.class);

		//공개설정이 없는 경우
		assertThatThrownBy(() ->
			mockMvc.perform(post("/write")
					.sessionAttr(SessionConst.LOGIN_MEMBER, 2l)
					.content("{"
						+ "\n\"title\":\"testtest\","
						+ "\n\"content\":\"333333\""
						+ "\n}")
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
		).isInstanceOf(Error.class);
	}

	@Test
	@Transactional
	@Rollback
	void 새글작성시_소개글을_주지_않거나_공백일_경우_본문에서_일부분을_추출한다() throws Exception {

		//소개글 주지 않은 경우
		mockMvc.perform(post("/write")
				.sessionAttr(SessionConst.LOGIN_MEMBER, 2l)
				.content("{"
					+ "\n\"title\":\"testtest\","
					+ "\n\"content\":\"333333\","
					+ "\n\"access\":\"PRIVATE\""
					+ "\n}")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());

		Query query = em.createNativeQuery("select MAX(post_id) from post");//방금 작성한 글의
		Long postId = ((BigInteger) query.getSingleResult()).longValue();

		query = em.createNativeQuery("select introduce from post where post_id = " + postId);//소개글
		assertThat(query.getSingleResult().equals("333333")).isTrue();

		//빈 걸 준 경우
		mockMvc.perform(post("/write")
				.sessionAttr(SessionConst.LOGIN_MEMBER, 2l)
				.content("{"
					+ "\n\"title\":\"testtest\","
					+ "\n\"introduce\":\"\","
					+ "\n\"content\":\"333333\","
					+ "\n\"access\":\"PRIVATE\""
					+ "\n}")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());

		query = em.createNativeQuery("select MAX(post_id) from post");
		postId = ((BigInteger) query.getSingleResult()).longValue();

		query = em.createNativeQuery("select introduce from post where post_id = " + postId);
		assertThat(query.getSingleResult().equals("333333")).isTrue();

		//공백 준 경우
		mockMvc.perform(post("/write")
				.sessionAttr(SessionConst.LOGIN_MEMBER, 2l)
				.content("{"
					+ "\n\"title\":\"testtest\","
					+ "\n\"introduce\":\"         \","
					+ "\n\"content\":\"333333\","
					+ "\n\"access\":\"PRIVATE\""
					+ "\n}")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());

		query = em.createNativeQuery("select MAX(post_id) from post");
		postId = ((BigInteger) query.getSingleResult()).longValue();

		query = em.createNativeQuery("select introduce from post where post_id = " + postId);
		assertThat(query.getSingleResult().equals("333333")).isTrue();
	}

	@Test
	@Transactional
	@Rollback
	void 새글작성시_status는_무조건_GENERAL_tempPost_없다() throws Exception {
		mockMvc.perform(post("/write")
				.sessionAttr(SessionConst.LOGIN_MEMBER, 2l)
				.content("{"
					+ "\n\"title\":\"testtest\","
					+ "\n\"content\":\"333333\","
					+ "\n\"access\":\"PRIVATE\""
					+ "\n}")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());

		Query query = em.createNativeQuery("select MAX(post_id) from post");
		Long postId = ((BigInteger) query.getSingleResult()).longValue();

		query = em.createNativeQuery("select status from post where post_id = " + postId);
		assertThat(query.getSingleResult().equals(PostStatus.GENERAL.name())).isTrue();
		query = em.createNativeQuery("select temporary_post_id from post where post_id = " + postId);
		assertThat(query.getSingleResult()).isNull();
	}

	@Test
	@Transactional
	@Rollback
	void 새글작성시_기존에_없는_태그만_태그테이블에_새로_추가된다() throws Exception {
		//전 태그개수
		Query query = em.createNativeQuery("select count(*) from tag");
		Long tagCnt = ((BigInteger) query.getSingleResult()).longValue();

		//있는 태그
		String existTag = "tag1";
		query = em.createNativeQuery("select count(*) from tag where name = ?").setParameter(1, existTag);
		long cnt = ((BigInteger) query.getSingleResult()).longValue();
		if (cnt != 1) {
			throw new IllegalStateException("있어야 하는데 없거나, 중복되는 태그이름이 있습니다.");
		}

		//없는 태그
		String notExistTag = "sirius";
		query = em.createNativeQuery("select count(*) from tag where name = ?").setParameter(1, notExistTag);
		cnt = ((BigInteger) query.getSingleResult()).longValue();
		if (cnt != 0) {
			throw new IllegalStateException("없어야 하는데 있는 태그이름이 있습니다.");
		}

		mockMvc.perform(post("/write")
				.sessionAttr(SessionConst.LOGIN_MEMBER, 2l)
				.content("{"
					+ "\n\"title\":\"testtest\","
					+ "\n\"content\":\"333333\","
					+ "\n\"access\":\"PRIVATE\","
					+ "\n\"tags\":[\"" + existTag + "\",\"" + notExistTag + "\"]"
					+ "\n}")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());

		//후 태그개수 - 전 태그개수
		query = em.createNativeQuery("select count(*) from tag");
		tagCnt = ((BigInteger) query.getSingleResult()).longValue() - tagCnt;

		assertThat(tagCnt == 1).isTrue();
	}

	@Test
	@Transactional
	@Rollback
	void 새글작성시_멤버는_무조건_있어야합니다() throws Exception {
		mockMvc.perform(post("/write")
				.sessionAttr(SessionConst.LOGIN_MEMBER, 2l)
				.content("{"
					+ "\n\"title\":\"testtest\","
					+ "\n\"content\":\"333333\","
					+ "\n\"access\":\"PRIVATE\""
					+ "\n}")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());

		Query query = em.createNativeQuery("select MAX(post_id) from post");
		Long postId = ((BigInteger) query.getSingleResult()).longValue();

		query = em.createNativeQuery("select member_id from post where post_id = " + postId);
		assertThat(query.getSingleResult()).isNotNull();
	}

	//writePOST - 기존글 수정

	//임시저장글을 해소하는 경우

	//새 임시저장

	//임시저장의 수정

	//기존글의 새 임시저장

	//기존글 임시저장의 수정
}
