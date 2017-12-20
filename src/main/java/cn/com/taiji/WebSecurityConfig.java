package cn.com.taiji;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	public void configureglobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("123").roles("USER").and().withUser("hekaifang").password("123").roles("ADMIN");
	} 
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/webjars/**", "/signup", "/about", "/","/userlist","/bootstrap3/**","/getuserpage","/jquery/**").permitAll()
				//.antMatchers("/new").hasAnyRole("USER")
				.anyRequest().authenticated()
				.and()
			    .formLogin()
				.loginPage("/login").defaultSuccessUrl("/userlist")
                .permitAll();
        http
        	.csrf().disable();
        
       /* http.authorizeRequests()
        .antMatchers("/new").hasAnyRole("USER");*/
	}
	
}
