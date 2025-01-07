package in.cp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); 
    }

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .authorizeHttpRequests(authorize -> authorize
	            .requestMatchers("/user/**").authenticated()
	            .requestMatchers("/login", "/resources/**", "/css/**", "/js/**").permitAll()
	            .anyRequest().permitAll()
	        )
	        .formLogin(login -> login
	            .loginPage("/login")
	            .defaultSuccessUrl("/user/allUsers", true)
	            .permitAll()
	        )
	        .logout(logout -> logout
	            .logoutUrl("/logout")
	            .logoutSuccessUrl("/login?logout")
	            .invalidateHttpSession(true)
	            .deleteCookies("JSESSIONID")
	            .permitAll()
	        )
	        .csrf(csrf -> csrf.disable());

	    return http.build();
	}
}