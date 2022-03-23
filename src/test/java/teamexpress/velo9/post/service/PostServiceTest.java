package teamexpress.velo9.post.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import teamexpress.velo9.post.domain.Post;
import teamexpress.velo9.post.domain.PostRepository;
import teamexpress.velo9.post.domain.PostStatus;
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
}
