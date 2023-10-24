package com.nbc.oauth2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {
	
	@Value("${google.logout_url}")
	private String logoutUrl;
	@Value("${google.login_process_path}")
	private String loginProcessPath;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(t ->
				{
					t.requestMatchers(new AntPathRequestMatcher("/login")).permitAll();
					t.anyRequest().authenticated();
				});
				//.requestMatchers("/secured").authenticated()
				//.anyRequest().permitAll()
			http.logout(t -> {
						t.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
						t.logoutSuccessHandler((req, resp, auth) -> new DefaultRedirectStrategy().sendRedirect(req, resp, logoutUrl));
						});
				// In this demo HTTP GET instead of POST is used for logout, 
				// so the logoutRequestMatcher is required to detect logout requests.
				//.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				//.logoutSuccessHandler((req, resp, auth) -> new DefaultRedirectStrategy().sendRedirect(req, resp, logoutUrl))
			http.oauth2Login(c -> {
				c.loginProcessingUrl(loginProcessPath);
				//c.loginPage("/oauth2/authorization/google");
				c.loginPage("/login");
				c.defaultSuccessUrl("http://localhost:9090/login/oauth2/code/google",true);
				//c.failureUrl("/login");
			});
		return http.build();
	}
	
}