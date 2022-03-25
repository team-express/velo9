package teamexpress.velo9.post.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.Transactional;
import teamexpress.velo9.post.dto.LovePostDTO;
import teamexpress.velo9.post.dto.TempSavedPostDTO;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class PostServiceTest {


    @Autowired
    private PostService postService;


	@Test
	void findTempSavedPosts() {
		// given
		List<TempSavedPostDTO> validTempSavedPosts = postService.getTempSavedPost(2L);
		List<TempSavedPostDTO> invalidTempSavedPosts = postService.getTempSavedPost(1L);

		// when
		int validPostsSize = validTempSavedPosts.size();
		int invalidPostsSize = invalidTempSavedPosts.size();

		// then
		assertThat(validPostsSize).isEqualTo(100);
		assertThat(invalidPostsSize).isEqualTo(0);
	}

	@Test
	void findLovePosts() {
		// given
		PageRequest pageRequest = PageRequest.of(0, 20, Sort.by(Direction.DESC, "createdDate"));
		Slice<LovePostDTO> lovePosts = postService.getLovePosts(2L, pageRequest);

		// when
		int lovePostsSize = lovePosts.getSize();
		LovePostDTO findLovePostDTO = lovePosts.stream()
			.filter(p -> p.getId() == 100)
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException());

		// then
		assertThat(lovePostsSize).isEqualTo(20);
		assertThat(findLovePostDTO.getTitle()).isEqualTo("99");
	}
}

