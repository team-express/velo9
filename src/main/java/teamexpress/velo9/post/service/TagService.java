package teamexpress.velo9.post.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamexpress.velo9.post.domain.PostTagRepository;
import teamexpress.velo9.post.domain.TagRepository;
import teamexpress.velo9.post.dto.TagDTO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagService {

	private final TagRepository tagRepository;
	private final PostTagRepository postTagRepository;

	@Transactional
	public void removeUselessTags() {
		tagRepository.findAll().stream().filter(tag -> postTagRepository.countByTag(tag) == 0)
			.forEach(tagRepository::delete);
	}

	public List<TagDTO> findAllTags(String nickname) {
		return tagRepository.findUsedTags(nickname).stream().map(TagDTO::new).collect(Collectors.toList());
	}
}
