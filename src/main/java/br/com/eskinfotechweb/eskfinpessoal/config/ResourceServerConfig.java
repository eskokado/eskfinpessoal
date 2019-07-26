package br.com.eskinfotechweb.eskfinpessoal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
//@EnableWebSecurity
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

//	@Autowired
//	private UserDetailsService userDetailsService;
	
//	@Autowired
//	private PasswordEncoder passwordEncoder;
		
//	@Autowired
//	public void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication()
//			.withUser("eskokado").password("3sk0k@d00").roles("ADMIN");
//		auth.userDetailsService(userDetailsService)
//			.passwordEncoder(passwordEncoder);
//	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/categorias").permitAll()
				.anyRequest().authenticated()
				.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.csrf().disable();
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.stateless(true);
	}
		    	
}
