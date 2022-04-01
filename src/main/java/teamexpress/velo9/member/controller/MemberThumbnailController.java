package teamexpress.velo9.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import teamexpress.velo9.member.api.MemberThumbnailFileUploader;
import teamexpress.velo9.member.dto.MemberThumbnailDTO;
import teamexpress.velo9.member.service.MemberService;

@RestController
@RequiredArgsConstructor
public class MemberThumbnailController {

	private final MemberThumbnailFileUploader memberThumbnailFileUploader;
	private final MemberService memberService;

	@PostMapping("/uploadMemberThumbnail")
	public MemberThumbnailDTO upload(MultipartFile uploadFile, Long memberId) {
		MemberThumbnailDTO memberThumbnailDTO = memberThumbnailFileUploader.upload(uploadFile);
		memberService.uploadThumbnail(memberThumbnailDTO, memberId);
		return memberThumbnailDTO;
	}

	@GetMapping("/displayMemberThumbnail")
	public ResponseEntity<byte[]> display(String fileName) {
		return new ResponseEntity<>(
			memberThumbnailFileUploader.getImage(fileName),
			memberThumbnailFileUploader.getHeader(fileName),
			HttpStatus.OK);
	}

	@PostMapping("/deleteMemberThumbnail")
	public void delete(String fileName, Long memberId) {
		memberThumbnailFileUploader.deleteFile(fileName);
		memberService.deleteThumbnail(memberId);
	}
}
