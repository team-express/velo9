package teamexpress.velo9.post.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import teamexpress.velo9.post.dto.SeriesReadDTO;
import teamexpress.velo9.post.service.SeriesService;

@RestController
@RequiredArgsConstructor
public class SeriesController {

	private final SeriesService seriesService;

	@GetMapping("/getSeriesList")
	public ResponseEntity<List<SeriesReadDTO>> getList() {
		return new ResponseEntity<>(seriesService.getAll(), HttpStatus.OK);
	}
}
