package com.udacity.jwdnd.course1.cloudstorage.config;

import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity //without this line you get the "Please sign in" login page
public class SecurityConfig extends WebSecurityConfigurerAdapter {
   private AuthenticationService authenticationService;

   public SecurityConfig(AuthenticationService authenticationService) {
      this.authenticationService = authenticationService;
   }

   @Override
   protected void configure(AuthenticationManagerBuilder auth) {
      auth.authenticationProvider(this.authenticationService);
   }

   @Override
   protected void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests()
              .antMatchers("/signup", "/login", "/css/**", "/js/**").permitAll()
              .anyRequest().authenticated();

      http.formLogin()
              .loginPage("/login")
              .permitAll()
              .defaultSuccessUrl("/home", true);

      /*http.logout(logout -> logout
              .logoutUrl("/login")
              .addLogoutHandler(new SecurityContextLogoutHandler())

      );*/
      http.logout()
              .invalidateHttpSession(true)
              .clearAuthentication(true)
              .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
              .logoutSuccessUrl("/login?logout");

   }

}
