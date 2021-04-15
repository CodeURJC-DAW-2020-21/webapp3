package es.dawequipo3.growing.security;


import es.dawequipo3.growing.security.jwt.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Order(1)
public class RestSecurityConfig extends WebSecurityConfigurerAdapter {

	@Qualifier("repositoryUserDetailsService")
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	//Expose AuthenticationManager as a Bean to be used in other services
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.antMatcher("/api/**");

		// URLs that need authentication to access to it
		//		RESTPlan
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/plans/like").hasRole("USER");
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/plans/dislike").hasRole("USER");
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/plans/likeC").hasRole("USER");
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/plans/dislikeC").hasRole("USER");
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/plans/edited").hasRole("ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/plans/done").hasRole("USER");
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/plans/new").hasRole("ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/plans/**").hasRole("ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/plans/completedPlans").hasRole("ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/plans/completedPlans").hasRole("ADMIN");

		//		RESTUser
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/users/profile/**").hasRole("USER");
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/users/profile/**").hasRole("USER");
		http.authorizeRequests().antMatchers("/api/users/image").hasRole("USER");
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/users/completedPlans").hasRole("ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/users").hasRole("ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/users").hasRole("ADMIN");
		http.authorizeRequests().antMatchers("/api/users/info").hasRole("USER");

		//		RESTCategory
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/categories/like").hasRole("USER");
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/categories/dislike").hasRole("USER");
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/categories/image").hasRole("USER");
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/categories/**").hasRole("ADMIN");

		// Other URLs can be accessed without authentication
		http.authorizeRequests().anyRequest().permitAll();

		// Disable CSRF protection (it is difficult to implement in REST APIs)
		http.csrf().disable();

		// Disable Http Basic Authentication
		http.httpBasic().disable();
		
		// Disable Form login Authentication
		http.formLogin().disable();

		// Avoid creating session 
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		// Add JWT Token filter
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

	}
}
