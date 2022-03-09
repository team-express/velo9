package teamexpress.velo9.post.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class TestController {

	@GetMapping("/tt")
	public String test() {
		return "test";
	}
}
