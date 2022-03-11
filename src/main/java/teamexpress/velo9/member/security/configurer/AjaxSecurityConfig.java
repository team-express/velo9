package teamexpress.velo9.member.security.configurer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import teamexpress.velo9.member.security.common.AjaxLoginAuthenticationEntryPoint;
import teamexpress.velo9.member.security.handler.AjaxAccessDeniedHandler;
import teamexpress.velo9.member.security.handler.AjaxAuthenticationFailureHandler;
import teamexpress.velo9.member.security.handler.AjaxAuthenticationSuccessHandler;
import teamexpress.velo9.member.security.provider.AjaxAuthenticationProvider;

@Configuration
@Order(0)
public class AjaxSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(ajaxAuthenticationProvider());
	}

	@Bean
	public AuthenticationSuccessHandler ajaxAuthenticationSuccessHandler() {
		return new AjaxAuthenticationSuccessHandler();
	}
	@Bean
	public AuthenticationFailureHandler ajaxAuthenticationFailureHandler() {
		return new AjaxAuthenticationFailureHandler();
	}

	@Bean
	public AuthenticationProvider ajaxAuthenticationProvider() {
		return new AjaxAuthenticationProvider();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.antMatcher("/api/**")
			.authorizeRequests()
			.antMatchers("/api/messages").hasRole("MANAGER")
			.anyRequest().authenticated();
//			.and()
//			.addFilterBefore(ajaxLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class);
		http
			.exceptionHandling()
			.authenticationEntryPoint(new AjaxLoginAuthenticationEntryPoint())
			.accessDeniedHandler(ajaxAccessDeniedHandler());
		http.csrf().disable();

		customConfigurerAjax(http);

	}

	private void customConfigurerAjax(HttpSecurity http) throws Exception {
		http.
			apply(new AjaxLoginConfigurer<>())
			.successHandlerAjax(ajaxAuthenticationSuccessHandler())
			.failureHandlerAjax(ajaxAuthenticationFailureHandler())
			.setAuthenticationManager(authenticationManagerBean())
			.loginProcessingUrl("/api/login");
	}

	@Bean
	public AccessDeniedHandler ajaxAccessDeniedHandler() {
		return new AjaxAccessDeniedHandler();
	}

//	@Bean
//	public AjaxLoginProcessingFilter ajaxLoginProcessingFilter() throws Exception {
//		AjaxLoginProcessingFilter ajaxLoginProcessingFilter = new AjaxLoginProcessingFilter();
//		ajaxLoginProcessingFilter.setAuthenticationManager(authenticationManagerBean());
//		ajaxLoginProcessingFilter.setAuthenticationSuccessHandler(ajaxAuthenticationSuccessHandler());
//		ajaxLoginProcessingFilter.setAuthenticationFailureHandler(ajaxAuthenticationFailureHandler());
//		return ajaxLoginProcessingFilter;
//	}

}