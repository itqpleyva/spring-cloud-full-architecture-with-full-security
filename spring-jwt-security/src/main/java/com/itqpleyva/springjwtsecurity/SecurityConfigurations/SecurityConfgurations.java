package com.itqpleyva.springjwtsecurity.SecurityConfigurations;

import com.itqpleyva.springjwtsecurity.JwtManagement.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfgurations extends WebSecurityConfigurerAdapter {
    
    @Autowired
	UserDetailsService userDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

       
        auth.userDetailsService(userDetailsService);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }    
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        http.csrf().disable()
                .authorizeRequests().antMatchers("/auth/authenticate").permitAll()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/auth/valid_token").hasAnyRole("ADMIN","MANAGER")
                .anyRequest().authenticated().and().
                exceptionHandling().and().sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
    
    @Bean
    public PasswordEncoder passwordencoder(){

        return NoOpPasswordEncoder.getInstance();
    }
}