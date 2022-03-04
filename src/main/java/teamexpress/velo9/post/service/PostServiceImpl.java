package teamexpress.velo9.post.service;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import teamexpress.velo9.post.dto.PostDTO;
import teamexpress.velo9.post.domain.Post;
import teamexpress.velo9.post.domain.PostRepository;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {
	private static final int FIRST_INDEX = 0;
	private static final int MAX_INTRODUCE_NUM = 150;

	private PostRepository postRepository;

	@Override
	public Long save(PostDTO postDTO) {

		if (isIntroduceNUll(postDTO)) {
			createIntroduce(postDTO);
		}

		Post post = dtoToEntity(postDTO);

		postRepository.save(post);

		return post.getId();
	}

	private static boolean isIntroduceNUll(PostDTO postDTO) {
		return postDTO.getIntroduce() == null;
	}

	private static boolean smallerThanMaxIntroduceNum(PostDTO postDTO) {
		return postDTO.getContent().length() < MAX_INTRODUCE_NUM;
	}

	private static void createIntroduce(PostDTO postDTO) {
		if (smallerThanMaxIntroduceNum(postDTO)) {
			postDTO.setIntroduce(postDTO.getContent());
			return;
		}
		postDTO.setIntroduce(
			postDTO.getContent().substring(FIRST_INDEX, MAX_INTRODUCE_NUM));
	}
}
