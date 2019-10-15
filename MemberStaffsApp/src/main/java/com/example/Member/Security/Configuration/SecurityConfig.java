package com.example.Member.Security.Configuration;


import com.example.Member.Security.Filter.JwtTokenFilter;
import com.example.Member.Security.Utilities.JwtAuthenticationEntryPoint;
import com.example.Member.Services.Security.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter
{

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
             auth
                 .userDetailsService(userDetailsService)
                 .passwordEncoder(getPasswordEncoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    PasswordEncoder getPasswordEncoder()
    {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
            http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/User/**")
                .permitAll()
                .antMatchers("/MemberStaffs/**")
                .fullyAuthenticated()
                .and()
                .httpBasic()
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);


            http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

    }
}
