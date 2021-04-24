package es.dawequipo3.growing.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    RepositoryUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10, new SecureRandom());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Public pages
        http.authorizeRequests().antMatchers("/static/**").permitAll();
        http.authorizeRequests().antMatchers("/").permitAll();
        http.authorizeRequests().antMatchers("/categories").permitAll();
        http.authorizeRequests().antMatchers("/explore").permitAll();
        http.authorizeRequests().antMatchers("/AboutUs").permitAll();
        http.authorizeRequests().antMatchers("/categoryInfo/**").permitAll();
        http.authorizeRequests().antMatchers("/getStarted/**").permitAll();
        http.authorizeRequests().antMatchers("/404-NotFound").permitAll();
        http.authorizeRequests().antMatchers("/500-ServerError").permitAll();


        // Only for not registered users
        http.authorizeRequests().antMatchers("/getStarted").anonymous();

        // Private pages (all other pages)
        http.authorizeRequests().antMatchers("/profile/**").hasAnyRole("USER");
        http.authorizeRequests().antMatchers("/profile/image").hasAnyRole("USER");
        http.authorizeRequests().antMatchers("/complete/**").hasAnyRole("USER");
        http.authorizeRequests().antMatchers("/editProfile").hasAnyRole("USER");
        http.authorizeRequests().antMatchers("/logout").hasAnyRole("USER");
        http.authorizeRequests().antMatchers("/editCategory/**").hasAnyRole("ADMIN");
        // Login form
        http.formLogin().loginPage("/getStarted");
        http.formLogin().usernameParameter("email");
        http.formLogin().passwordParameter("password");
        http.formLogin().defaultSuccessUrl("/profile");
        http.formLogin().failureUrl("/getStarted");

        // Logout
        http.logout().logoutUrl("/logout");
        http.logout().logoutSuccessUrl("/");

    }
}
