package teamexpress.velo9.member.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import teamexpress.velo9.member.domain.Member;
import teamexpress.velo9.member.dto.MemberSignupDTO;
import teamexpress.velo9.member.security.oauth.SessionConst;

public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException {
		Member member = (Member) authentication.getPrincipal();
		MemberSignupDTO memberSignupDTO = new MemberSignupDTO(member);
		HttpSession session = request.getSession();
		session.setAttribute(SessionConst.LOGIN_MEMBER, member.getId());
		response.setStatus(HttpStatus.OK.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		objectMapper.writeValue(response.getWriter(), memberSignupDTO);
	}
}
