package teamexpress.velo9.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import teamexpress.velo9.common.domain.Result;
import teamexpress.velo9.post.dto.SeriesAddDTO;
import teamexpress.velo9.post.service.SeriesService;

@RestController
@RequiredArgsConstructor
public class SeriesController {

	private final SeriesService seriesService;

	@GetMapping("/getSeriesList")
	public Result getSeriesList(@RequestParam Long memberId) {
		return new Result(seriesService.getAll(memberId));
	}

	@PostMapping("/addSeries")
	public void addSeries(@RequestBody SeriesAddDTO seriesAddDTO) {
		seriesService.add(seriesAddDTO);
	}

	@PostMapping("/deleteSeries")
	public void deleteSeries(@RequestParam Long id) {
		seriesService.delete(id);
	}
}
