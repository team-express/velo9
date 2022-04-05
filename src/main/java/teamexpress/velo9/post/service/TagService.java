package teamexpress.velo9.post.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamexpress.velo9.post.domain.Post;
import teamexpress.velo9.post.domain.PostRepository;
import teamexpress.velo9.post.domain.PostTag;
import teamexpress.velo9.post.domain.PostTagRepository;
import teamexpress.velo9.post.domain.Tag;
import teamexpress.velo9.post.domain.TagRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagService {

	private final PostRepository postRepository;
	private final TagRepository tagRepository;
	private final PostTagRepository postTagRepository;

	@Transactional
	public void addTags(Long postId, List<String> tagNames) {
		if (tagNames == null) {
			return;
		}

		List<String> tags = removeDuplication(tagNames);

		Post post = postRepository.findById(postId).orElse(null);

		List<String> realTagNames = tagRepository.getTagNames();

		tags.stream().filter(name -> !realTagNames.contains(name))
			.forEach((name) -> tagRepository.save(Tag.builder().name(name).build()));

		postTagRepository.deleteAllByPost(post);

		tags.forEach(name -> postTagRepository.save(
			PostTag.builder()
				.tag(tagRepository.findByName(name))
				.post(post)
				.build()
		));
	}

	@Transactional
	public void removeUselessTags() {
		tagRepository.findAll().stream().filter(tag -> postTagRepository.countByTag(tag) == 0)
			.forEach(tagRepository::delete);
	}

	private List removeDuplication(List<String> tagNames) {
		return tagNames.stream().distinct().collect(Collectors.toList());
	}
}
