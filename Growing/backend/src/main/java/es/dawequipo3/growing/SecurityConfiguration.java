package es.dawequipo3.growing;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${security.user}")
    private String user;
    @Value("${security.encodedPassword}")
    private String encodedPassword;

    @Value("${security.admin}")
    private String admin;
    @Value("${security.encodedPassword}")
    private String encodedPasswordAdmin;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        auth.inMemoryAuthentication().withUser(user).password("{bcrypt}"+encodedPassword).roles("USER");
        auth.inMemoryAuthentication().withUser(admin).password("{bcrypt}"+encodedPasswordAdmin).roles("ADMIN", "USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Public pages
        http.authorizeRequests().antMatchers("/assets/**").permitAll();
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
        http.authorizeRequests().antMatchers("/profile").hasAnyRole("USER");
        http.authorizeRequests().antMatchers("/complete/**").hasAnyRole("USER");
        http.authorizeRequests().antMatchers("/editProfile").hasAnyRole("USER");
        http.authorizeRequests().antMatchers("/profileAdmin").hasAnyRole("ADMIN");

        // Login form
        http.formLogin().loginPage("/getStarted");
        http.formLogin().usernameParameter("email");
        http.formLogin().passwordParameter("password");
        http.formLogin().defaultSuccessUrl("/profile");
        http.formLogin().failureUrl("/getStarted");

        // Logout
        http.logout().logoutUrl("/logout");
        http.logout().logoutSuccessUrl("/");

        // Disable CSRF at the moment
        http.csrf().disable();
    }
}
