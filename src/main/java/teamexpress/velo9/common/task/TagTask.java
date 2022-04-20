package teamexpress.velo9.common.task;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import teamexpress.velo9.post.domain.PostTagRepository;
import teamexpress.velo9.post.domain.TagRepository;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagTask {

	private final TagRepository tagRepository;
	private final PostTagRepository postTagRepository;

	@Scheduled(cron = "0 54 13 * * *")
	@Transactional
	public void removeUselessTags() {
		tagRepository.findAll().stream().filter(tag -> postTagRepository.findFirstByTag(tag).isEmpty())
			.forEach(tagRepository::delete);
	}
}
