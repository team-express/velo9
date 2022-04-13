package teamexpress.velo9.member.security.configure;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import teamexpress.velo9.member.security.common.CustomLoginAuthenticationEntryPoint;
import teamexpress.velo9.member.security.handler.CustomAccessDeniedHandler;
import teamexpress.velo9.member.security.handler.CustomAuthenticationFailureHandler;
import teamexpress.velo9.member.security.handler.CustomAuthenticationSuccessHandler;
import teamexpress.velo9.member.security.oauth.CustomOAuth2UserService;
import teamexpress.velo9.member.security.provider.CustomAuthenticationProvider;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final CustomOAuth2UserService customOAuth2UserService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public AuthenticationSuccessHandler ajaxAuthenticationSuccessHandler() {
		return new CustomAuthenticationSuccessHandler();
	}

	@Bean
	public AuthenticationFailureHandler ajaxAuthenticationFailureHandler() {
		return new CustomAuthenticationFailureHandler();
	}

	@Bean
	public AuthenticationProvider ajaxAuthenticationProvider() {
		return new CustomAuthenticationProvider();
	}

	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.httpBasic().disable()
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/","/getHeaderInfo","/signup","/sendMail","/certifyNumber","/checkFirstLogin","/socialSignup","/findId","/findPw","/changePasswordAfterFindPW","/memberLogout","/validateUsername","/validateNickname")
			.permitAll()
			.antMatchers("/{nickname}/series","/{nickname}/series/{seriesName}","/{nickname}/main","/{nickname}/read/{postId}")
			.permitAll()
			.antMatchers("/setting","/changePassword","/withdraw").hasRole("USER")
			.antMatchers("/write","/writeTemporary","/delete","/temp","/love","/archive/like","/archive/recent").hasRole("USER")
			.antMatchers("/nothing").hasRole("ADMIN")
			.anyRequest().authenticated()
			.and()
			.oauth2Login()
			.defaultSuccessUrl("/checkFirstLogin")
			.userInfoEndpoint()
			.userService(customOAuth2UserService);

		http
			.logout()
			.logoutUrl("/logout")
			.logoutSuccessUrl("/nothing");

		http
			.exceptionHandling()
			.authenticationEntryPoint(new CustomLoginAuthenticationEntryPoint())
			.accessDeniedHandler(accessDeniedHandler());

		customConfigurer(http);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(ajaxAuthenticationProvider());
	}

	private void customConfigurer(HttpSecurity http) throws Exception {
		http.
			apply(new CustomLoginConfigurer<>())
			.successHandlerAjax(ajaxAuthenticationSuccessHandler())
			.failureHandlerAjax(ajaxAuthenticationFailureHandler())
			.setAuthenticationManager(authenticationManagerBean())
			.loginProcessingUrl("/login");
	}
}

