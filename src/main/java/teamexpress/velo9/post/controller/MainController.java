package teamexpress.velo9.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import teamexpress.velo9.post.dto.PostMainDTO;
import teamexpress.velo9.post.dto.SearchCondition;
import teamexpress.velo9.post.service.PostService;

@Controller
@RequiredArgsConstructor
public class MainController {

	private final PostService postService;

	@GetMapping("/")
	public ResponseEntity<Page<PostMainDTO>> mainPage(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "20") int size) {

		PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Direction.DESC, "createdDate"));
		Page<PostMainDTO> mainPage = postService.getMainPage(pageRequest);
		return new ResponseEntity<>(mainPage, HttpStatus.OK);
	}

	@GetMapping("/searchMain")
	public ResponseEntity<Page<PostMainDTO>> searchMain(
		@RequestParam(required = false) boolean tagSelect,
		@RequestParam(required = false) String content,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "20") int size,
		@RequestParam(defaultValue = "createdDate") String sortValue) {

		PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Direction.DESC, sortValue));

		SearchCondition searchCondition = new SearchCondition(tagSelect, content);
		Page<PostMainDTO> mainSearchPage = postService.searchMain(searchCondition, pageRequest);

		return new ResponseEntity<>(mainSearchPage, HttpStatus.OK);
	}

}
