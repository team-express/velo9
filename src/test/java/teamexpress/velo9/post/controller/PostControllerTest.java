package teamexpress.velo9.post.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
class PostControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void series() throws Exception {
		this.mockMvc.perform(get("/{nickname}/series", "admin")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("GetSeries",
				responseFields(
					fieldWithPath("content.[].seriesName").description("password 내용").optional(),
					fieldWithPath("content.[].posts.[].title").description("password 내용").optional(),
					fieldWithPath("content.[].posts.[].introduce").description("password 내용").optional(),
					fieldWithPath("content.[].posts.[].thumbnail.uuid").description("password 내용").optional(),
					fieldWithPath("content.[].posts.[].thumbnail.name").description("password 내용").optional(),
					fieldWithPath("content.[].posts.[].thumbnail.path").description("password 내용").optional(),
					fieldWithPath("pageable.sort.empty").description("password 내용").optional(),
					fieldWithPath("pageable.sort.unsorted").description("password 내용").optional(),
					fieldWithPath("pageable.sort.sorted").description("password 내용").optional(),
					fieldWithPath("pageable.offset").description("password 내용").optional(),
					fieldWithPath("pageable.pageNumber").description("password 내용").optional(),
					fieldWithPath("pageable.pageSize").description("password 내용").optional(),
					fieldWithPath("pageable.paged").description("password 내용").optional(),
					fieldWithPath("pageable.unpaged").description("password 내용").optional(),
					fieldWithPath("size").description("password 내용").optional(),
					fieldWithPath("sort.empty").description("password 내용").optional(),
					fieldWithPath("sort.unsorted").description("password 내용").optional(),
					fieldWithPath("sort.sorted").description("password 내용").optional(),
					fieldWithPath("number").description("password 내용").optional(),
					fieldWithPath("first").description("password 내용").optional(),
					fieldWithPath("last").description("password 내용").optional(),
					fieldWithPath("numberOfElements").description("password 내용").optional(),
					fieldWithPath("empty").description("password 내용").optional()
				)
			));
	}

	@Test
	void seriesPost() throws Exception {
		this.mockMvc.perform(get("/{nickname}/series/{seriesName}", "admin", "series1")
				.param("memberId", "2")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("GetSeriesPost",
				responseFields(
					fieldWithPath("content.[].id").description("포스트의 ID").optional(),
					fieldWithPath("content.[].title").description("제목").optional(),
					fieldWithPath("content.[].seriesName").description("시리즈 네임").optional(),
					fieldWithPath("content.[].introduce").description("소개글").optional(),
					fieldWithPath("content.[].createdDate").description("작성날짜").optional(),
					fieldWithPath("content.[].thumbnail.uuid").description("썸네일 uuid").optional(),
					fieldWithPath("content.[].thumbnail.name").description("썸네일 name").optional(),
					fieldWithPath("content.[].thumbnail.path").description("썸네일 path").optional(),
					fieldWithPath("content.[].postTags").description("포스트 태그들").optional(),
					fieldWithPath("pageable.sort.empty").description("password 내용").optional(),
					fieldWithPath("pageable.sort.unsorted").description("password 내용").optional(),
					fieldWithPath("pageable.sort.sorted").description("password 내용").optional(),
					fieldWithPath("pageable.offset").description("password 내용").optional(),
					fieldWithPath("pageable.pageNumber").description("password 내용").optional(),
					fieldWithPath("pageable.pageSize").description("password 내용").optional(),
					fieldWithPath("pageable.paged").description("password 내용").optional(),
					fieldWithPath("pageable.unpaged").description("password 내용").optional(),
					fieldWithPath("size").description("password 내용").optional(),
					fieldWithPath("sort.empty").description("password 내용").optional(),
					fieldWithPath("sort.unsorted").description("password 내용").optional(),
					fieldWithPath("sort.sorted").description("password 내용").optional(),
					fieldWithPath("number").description("password 내용").optional(),
					fieldWithPath("first").description("password 내용").optional(),
					fieldWithPath("last").description("password 내용").optional(),
					fieldWithPath("numberOfElements").description("password 내용").optional(),
					fieldWithPath("empty").description("password 내용").optional()
				)
			));
	}

	@Test
	void postsRead() throws Exception {
		this.mockMvc.perform(get("/{nickname}/main", "admin")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("GetPostsRead",
				responseFields(
					fieldWithPath("content.[].title").description("제목").optional(),
					fieldWithPath("content.[].introduce").description("소개글").optional(),
					fieldWithPath("content.[].createdDate").description("작성날짜").optional(),
					fieldWithPath("content.[].replyCount").description("댓글 수").optional(),
					fieldWithPath("content.[].thumbnail.uuid").description("썸네일 uuid").optional(),
					fieldWithPath("content.[].thumbnail.name").description("썸네일 name").optional(),
					fieldWithPath("content.[].thumbnail.path").description("썸네일 path").optional(),
					fieldWithPath("content.[].postTags").description("포스트 태그들").optional(),
					fieldWithPath("pageable.sort.empty").description("password 내용").optional(),
					fieldWithPath("pageable.sort.unsorted").description("password 내용").optional(),
					fieldWithPath("pageable.sort.sorted").description("password 내용").optional(),
					fieldWithPath("pageable.offset").description("password 내용").optional(),
					fieldWithPath("pageable.pageNumber").description("password 내용").optional(),
					fieldWithPath("pageable.pageSize").description("password 내용").optional(),
					fieldWithPath("pageable.paged").description("password 내용").optional(),
					fieldWithPath("pageable.unpaged").description("password 내용").optional(),
					fieldWithPath("size").description("password 내용").optional(),
					fieldWithPath("sort.empty").description("password 내용").optional(),
					fieldWithPath("sort.unsorted").description("password 내용").optional(),
					fieldWithPath("sort.sorted").description("password 내용").optional(),
					fieldWithPath("number").description("password 내용").optional(),
					fieldWithPath("first").description("password 내용").optional(),
					fieldWithPath("last").description("password 내용").optional(),
					fieldWithPath("numberOfElements").description("password 내용").optional(),
					fieldWithPath("empty").description("password 내용").optional()
				)
			));
	}

	@Test
	void tempPostsRead() throws Exception {
		this.mockMvc.perform(get("/temp")
				.param("memberId", "2")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("GetTemp",
				responseFields(
					fieldWithPath("data.[].title").description("제목").optional(),
					fieldWithPath("data.[].content").description("소개글").optional(),
					fieldWithPath("data.[].createdDate").description("작성날짜").optional()
				)
			));
	}

	@Test
	void lovePostRead() throws Exception {
		this.mockMvc.perform(get("/archive/like")
				.param("memberId", "2")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("GetLovePostRead",
				responseFields(
					fieldWithPath("content.[].id").description("제목").optional(),
					fieldWithPath("content.[].title").description("제목").optional(),
					fieldWithPath("content.[].introduce").description("소개글").optional(),
					fieldWithPath("content.[].createdDate").description("작성날짜").optional(),
					fieldWithPath("pageable.sort.empty").description("password 내용").optional(),
					fieldWithPath("pageable.sort.unsorted").description("password 내용").optional(),
					fieldWithPath("pageable.sort.sorted").description("password 내용").optional(),
					fieldWithPath("pageable.offset").description("password 내용").optional(),
					fieldWithPath("pageable.pageNumber").description("password 내용").optional(),
					fieldWithPath("pageable.pageSize").description("password 내용").optional(),
					fieldWithPath("pageable.paged").description("password 내용").optional(),
					fieldWithPath("pageable.unpaged").description("password 내용").optional(),
					fieldWithPath("size").description("password 내용").optional(),
					fieldWithPath("sort.empty").description("password 내용").optional(),
					fieldWithPath("sort.unsorted").description("password 내용").optional(),
					fieldWithPath("sort.sorted").description("password 내용").optional(),
					fieldWithPath("number").description("password 내용").optional(),
					fieldWithPath("first").description("password 내용").optional(),
					fieldWithPath("last").description("password 내용").optional(),
					fieldWithPath("numberOfElements").description("password 내용").optional(),
					fieldWithPath("empty").description("password 내용").optional()
				)
			));
	}

	@Test
	void lookPostRead() throws Exception {
		this.mockMvc.perform(get("/archive/recent")
				.param("memberId", "2")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("GetLookPostRead",
				responseFields(
					fieldWithPath("content.[].id").description("제목").optional(),
					fieldWithPath("content.[].title").description("제목").optional(),
					fieldWithPath("content.[].introduce").description("소개글").optional(),
					fieldWithPath("content.[].createdDate").description("작성날짜").optional(),
					fieldWithPath("pageable.sort.empty").description("password 내용").optional(),
					fieldWithPath("pageable.sort.unsorted").description("password 내용").optional(),
					fieldWithPath("pageable.sort.sorted").description("password 내용").optional(),
					fieldWithPath("pageable.offset").description("password 내용").optional(),
					fieldWithPath("pageable.pageNumber").description("password 내용").optional(),
					fieldWithPath("pageable.pageSize").description("password 내용").optional(),
					fieldWithPath("pageable.paged").description("password 내용").optional(),
					fieldWithPath("pageable.unpaged").description("password 내용").optional(),
					fieldWithPath("size").description("password 내용").optional(),
					fieldWithPath("sort.empty").description("password 내용").optional(),
					fieldWithPath("sort.unsorted").description("password 내용").optional(),
					fieldWithPath("sort.sorted").description("password 내용").optional(),
					fieldWithPath("number").description("password 내용").optional(),
					fieldWithPath("first").description("password 내용").optional(),
					fieldWithPath("last").description("password 내용").optional(),
					fieldWithPath("numberOfElements").description("password 내용").optional(),
					fieldWithPath("empty").description("password 내용").optional()
				)
			));
	}

	@Test
	void readPost() throws Exception {
		this.mockMvc.perform(get("/{nickname}/read/{postId}", "admin", "2")
				.param("memberId", "2")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("GetReadPost",
				responseFields(
					fieldWithPath("data.[].title").description("제목").optional(),
					fieldWithPath("data.[].seriesName").description("소개글").optional(),
					fieldWithPath("data.[].content").description("소개글").optional(),
					fieldWithPath("data.[].loveCount").description("소개글").optional(),
					fieldWithPath("data.[].createdDate").description("작성날짜").optional(),
					fieldWithPath("data.[].memberDTO.memberName").description("작성날짜").optional(),
					fieldWithPath("data.[].memberDTO.memberIntroduce").description("작성날짜").optional(),
					fieldWithPath("data.[].memberDTO.socialGithub").description("작성날짜").optional(),
					fieldWithPath("data.[].memberDTO.socialEmail").description("작성날짜").optional(),
					fieldWithPath("data.[].memberDTO.memberThumbnailDTO.uuid").description("작성날짜").optional(),
					fieldWithPath("data.[].memberDTO.memberThumbnailDTO.name").description("작성날짜").optional(),
					fieldWithPath("data.[].memberDTO.memberThumbnailDTO.path").description("작성날짜").optional(),
					fieldWithPath("data.[].postTags").description("작성날짜").optional(),
					fieldWithPath("data.[].prevPost").description("작성날짜").optional(),
					fieldWithPath("data.[].nextPost").description("작성날짜").optional()
				)
			));
	}

}
