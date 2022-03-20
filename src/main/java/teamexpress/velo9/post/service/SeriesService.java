package teamexpress.velo9.post.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teamexpress.velo9.post.domain.SeriesRepository;
import teamexpress.velo9.post.dto.SeriesReadDTO;

@Service
@RequiredArgsConstructor
public class SeriesService {

	private final SeriesRepository seriesRepository;

	public List<SeriesReadDTO> getAll() {
		return seriesRepository.findAll().stream().map(series -> new SeriesReadDTO(series)).collect(
			Collectors.toList());
	}
}
