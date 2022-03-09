package teamexpress.velo9.post.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import teamexpress.velo9.post.domain.Post;
import teamexpress.velo9.post.domain.PostRepository;
import teamexpress.velo9.post.dto.PostDTO;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
	private static final int FIRST_INDEX = 0;
	private static final int MAX_INTRODUCE = 150;

	private final PostRepository postRepository;

	private static boolean isIntroduceNUll(PostDTO postDTO) {
		return postDTO.getIntroduce() == null;
	}

	private static boolean smallerThanMaxIntroduceNum(PostDTO postDTO) {
		return postDTO.getContent().length() < MAX_INTRODUCE;
	}

	private static void createIntroduce(PostDTO postDTO) {
		if (smallerThanMaxIntroduceNum(postDTO)) {
			postDTO.setIntroduce(postDTO.getContent());
			return;
		}
		postDTO.setIntroduce(
			postDTO.getContent().substring(FIRST_INDEX, MAX_INTRODUCE));
	}

	@Override
	public Long save(PostDTO postDTO) {

		if (isIntroduceNUll(postDTO)) {
			createIntroduce(postDTO);
		}

		Post post = dtoToEntity(postDTO);

		postRepository.save(post);

		return post.getId();
	}
}
