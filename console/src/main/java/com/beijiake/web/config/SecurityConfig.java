package com.beijiake.web.config;

import com.beijiake.web.security.DaoUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService MyUserDetailsService() {
        return new DaoUserDetailService();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        http.formLogin().loginPage("/login");
        http.csrf().disable();

        // DEBUG: disable the security check
        http.authorizeRequests().antMatchers("/about").authenticated().anyRequest().permitAll();



//        http.authorizeRequests()
//                .antMatchers("/login", "/webjars/**", "/register").permitAll()
//                .antMatchers(HttpMethod.POST, "/register").permitAll()
//                .anyRequest().authenticated();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.inMemoryAuthentication().withUser("user").password(passwordEncoder().encode("123456")).authorities("ROLE_USER");

        auth.userDetailsService(MyUserDetailsService());
    }
}
