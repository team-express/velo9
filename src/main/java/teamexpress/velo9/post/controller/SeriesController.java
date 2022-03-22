package teamexpress.velo9.post.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import teamexpress.velo9.post.dto.SeriesAddDTO;
import teamexpress.velo9.post.dto.SeriesReadDTO;
import teamexpress.velo9.post.service.SeriesService;

@RestController
@RequiredArgsConstructor
public class SeriesController {

	private final SeriesService seriesService;

	@GetMapping("/getSeriesList")
	public ResponseEntity<List<SeriesReadDTO>> getSeriesList(@RequestParam("memberId") Long memberId) {
		return new ResponseEntity<>(seriesService.getAll(memberId), HttpStatus.OK);
	}

	@PostMapping("/addSeries")
	public void addSeries(@RequestBody SeriesAddDTO seriesAddDTO) {
		seriesService.add(seriesAddDTO);
	}

	@PostMapping("/deleteSeries")
	public void deleteSeries(@RequestParam("seriesId") Long seriesId) {
		seriesService.delete(seriesId);
	}
}
