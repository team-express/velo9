package teamexpress.velo9.member.security.configure;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import teamexpress.velo9.member.security.common.AjaxLoginAuthenticationEntryPoint;
import teamexpress.velo9.member.security.handler.AjaxAccessDeniedHandler;
import teamexpress.velo9.member.security.handler.AjaxAuthenticationFailureHandler;
import teamexpress.velo9.member.security.handler.AjaxAuthenticationSuccessHandler;
import teamexpress.velo9.member.security.oauth.CustomOAuth2UserService;
import teamexpress.velo9.member.security.provider.AjaxAuthenticationProvider;

@RequiredArgsConstructor
@Configuration
public class AjaxSecurityConfig extends WebSecurityConfigurerAdapter {

	private final CustomOAuth2UserService customOAuth2UserService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
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

	@Bean
	public AccessDeniedHandler ajaxAccessDeniedHandler() {
		return new AjaxAccessDeniedHandler();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.httpBasic().disable()
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/signup", "/sendMail", "/{nickname}/temp", "/sessionLogin", "/**")
			.permitAll()
			.anyRequest().authenticated()
			.and()
			.oauth2Login()
			.defaultSuccessUrl("/checkFirstLogin")
			.userInfoEndpoint()
			.userService(customOAuth2UserService);

		http
			.exceptionHandling()
			.authenticationEntryPoint(new AjaxLoginAuthenticationEntryPoint())
			.accessDeniedHandler(ajaxAccessDeniedHandler());

		customConfigurerAjax(http);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(ajaxAuthenticationProvider());
	}

	private void customConfigurerAjax(HttpSecurity http) throws Exception {
		http.
			apply(new AjaxLoginConfigurer<>())
			.successHandlerAjax(ajaxAuthenticationSuccessHandler())
			.failureHandlerAjax(ajaxAuthenticationFailureHandler())
			.setAuthenticationManager(authenticationManagerBean())
			.loginProcessingUrl("/api/login");
	}
}

