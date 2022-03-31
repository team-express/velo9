package teamexpress.velo9.member.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
class MemberControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@Transactional
	@Rollback
	void addMember() throws Exception {
		this.mockMvc.perform(post("/signup")
				.content("{\"username\": \"username33\","
					+ " \n\"password\": \"password\","
					+ "\n\"nickname\": \"nickname33\","
					+ "\n\"email\": \"email3332@test.com\"}")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("addMember",
				requestFields(
					fieldWithPath("username").description("username 내용").optional(),
					fieldWithPath("password").description("password 내용").optional(),
					fieldWithPath("nickname").description("nickname 내용").optional(),
					fieldWithPath("email").description("email 내용").optional()
				)
			));
	}

	@Test
	void GetEditMember() throws Exception {
		this.mockMvc.perform(get("/setting")
				.param("memberId","2")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("GetEditMember",
				responseFields(
					fieldWithPath("username").description("username 내용").optional(),
					fieldWithPath("nickname").description("nickname 내용").optional(),
					fieldWithPath("email").description("email 내용").optional(),
					fieldWithPath("introduce").description("introduce 내용").optional(),
					fieldWithPath("blogTitle").description("blogTitle 내용").optional(),
					fieldWithPath("socialEmail").description("socialEmail 내용").optional(),
					fieldWithPath("socialGithub").description("socialGithub 내용").optional()
				)
			));
	}

	@Test
	@Transactional
	@Rollback
	void PostEditMember() throws Exception {
		this.mockMvc.perform(post("/setting")
				.param("memberId","3")
				.content("{\"username\": \"username\", \"nickname\": \"nickname\","
					+ "\n\"password\": \"password\", \"email\": \"email@test.com\","
					+ "\n\"introduce\": \"introduce\", \"blogTitle\": \"blogTitle\","
					+ "\n\"socialEmail\": \"socialEmail\", \"socialGithub\": \"socialGithub\"}")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("PostEditMember",
				requestFields(
					fieldWithPath("username").description("username 내용").optional(),
					fieldWithPath("nickname").description("nickname 내용").optional(),
					fieldWithPath("password").description("password 내용").optional(),
					fieldWithPath("email").description("email 내용").optional(),
					fieldWithPath("introduce").description("introduce 내용").optional(),
					fieldWithPath("blogTitle").description("blogTitle 내용").optional(),
					fieldWithPath("socialEmail").description("socialEmail 내용").optional(),
					fieldWithPath("socialGithub").description("socialGithub 내용").optional()
				),
				responseFields(
					fieldWithPath("username").description("username 내용").optional(),
					fieldWithPath("nickname").description("nickname 내용").optional(),
					fieldWithPath("email").description("email 내용").optional(),
					fieldWithPath("introduce").description("introduce 내용").optional(),
					fieldWithPath("blogTitle").description("blogTitle 내용").optional(),
					fieldWithPath("socialEmail").description("socialEmail 내용").optional(),
					fieldWithPath("socialGithub").description("socialGithub 내용").optional()
				)
			));
	}

	@Test
	@Transactional
	@Rollback
	void changePassword() throws Exception {
		this.mockMvc.perform(post("/changePassword")
				.param("memberId", "3")
				.content("{\"oldPassword\": \"1234\", \n\"newPassword\": \"0000\"}")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("PostChangePw",
				requestFields(
					fieldWithPath("oldPassword").description("이전 비밀번호").optional(),
					fieldWithPath("newPassword").description("새로운 비밀번호").optional()
				)
			));
	}

	@Test
	@Transactional
	@Rollback
	void withdraw() throws Exception {
		this.mockMvc.perform(post("/withdraw")
				.param("memberId", "1")
				.content("{\"oldPassword\": \"1234\"}")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("PostWithdraw",
				requestFields(
					fieldWithPath("oldPassword").description("이전 비밀번호").optional()
				)
			));
	}

	@Test
	@Transactional
	@Rollback
	void socialSignup() throws Exception {
		this.mockMvc.perform(post("/socialSignup")
				.param("memberId", "4")
				.content("{\"username\": \"username\","
					+ "\n\"password\": \"password\","
					+ "\n\"nickname\": \"nickname\"}")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("PostSocialSignup",
				requestFields(
					fieldWithPath("username").description("회원 아이디").optional(),
					fieldWithPath("password").description("비밀번호").optional(),
					fieldWithPath("nickname").description("닉네임").optional()
				)
			));
	}

	@Test
	void sendMail() throws Exception {
		this.mockMvc.perform(post("/sendMail")
				.content("{\"email\": \"email2@test.com\"}")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("PostSendMail",
				requestFields(
					fieldWithPath("email").description("회원가입시 입력하는 이메일").optional()
				),
				responseFields(
					fieldWithPath("data").description("인증번호").optional()
				)
			));
	}

	@Test
	@Transactional
	@Rollback
	void findId() throws Exception {
		this.mockMvc.perform(post("/findId")
				.content("{\"email\": \"test1@nate.com\"}")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("PostFindId",
				requestFields(
					fieldWithPath("email").description("회원가입시 입력했던 이메일").optional()
				)
			));
	}

	@Test
	@Transactional
	@Rollback
	void findPw() throws Exception {
		this.mockMvc.perform(post("/findPw")
				.content("{\"username\":\"test\",\"email\":\"test3@nate.com\"}")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("PostFindPw",
				requestFields(
					fieldWithPath("username").description("회원가입시 입력했던 아이디").optional(),
					fieldWithPath("email").description("회원가입시 입력했던 이메일").optional()
				)
			));
	}

	@Test
	@Transactional
	@Rollback
	void sendMailPw() throws Exception {
		this.mockMvc.perform(post("/sendMailPw")
				.content("{\"email\":\"test2@nate.com\"}")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("PostFindPw",
				requestFields(
					fieldWithPath("email").description("회원가입시 입력했던 이메일").optional()
				)
			));
	}

	@Test
	@Transactional
	@Rollback
	void changePw() throws Exception {
		this.mockMvc.perform(post("/changePw")
				.content("{\"id\":\"2\", \"password\": \"0000\"}")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("PostFindPw",
				requestFields(
					fieldWithPath("id").description("DB상의 회원 ID").optional(),
					fieldWithPath("password").description("새로운 비밀번호").optional()
				)
			));
	}
}
