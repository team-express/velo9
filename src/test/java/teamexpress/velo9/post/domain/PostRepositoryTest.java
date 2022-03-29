package teamexpress.velo9.post.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;
import teamexpress.velo9.post.dto.SearchCondition;

import java.util.List;

@SpringBootTest
@Transactional
class PostRepositoryTest {

	@Autowired
	private PostRepository postRepository;

	@Test
	void findPost() {
		//given
		String nickname = "admin";
		PageRequest pageRequest = PageRequest.of(0, 10);

		Slice<Post> findPost = postRepository.findPost(nickname, pageRequest);
		System.out.println("findPost = " + findPost);

		//when
		int numberOfElements = findPost.getNumberOfElements();

		//then
		assertThat(numberOfElements).isEqualTo(10);
		assertThat(findPost).extracting("title").contains("1", "2", "3", "4", "5", "6", "7", "8", "9");
		assertThat(findPost).extracting("introduce").contains("1", "2", "3", "4", "5", "6", "7", "8", "9");
	}

	@Test
	void searchMain() {
		//given
		SearchCondition searchCondition = new SearchCondition(false, "1");
		PageRequest pageRequest = PageRequest.of(0, 10);

		//when
		Page<Post> posts = postRepository.search(searchCondition, pageRequest);

		//then
		for (Post post : posts) {
			System.out.println("post = " + post);
		}
	}

	@Test
	void findTempSavedPosts() {
		// given
		List<Post> validPosts = postRepository.getTempSavedPost(2L, PostStatus.TEMPORARY);
		List<Post> invalidPosts = postRepository.getTempSavedPost(1L, PostStatus.TEMPORARY);

		// when
		int validPostsSize = validPosts.size();
		int invalidPostsSize = invalidPosts.size();

		// then
		assertThat(validPostsSize).isEqualTo(100);
		assertThat(invalidPostsSize).isEqualTo(0);
	}
}
