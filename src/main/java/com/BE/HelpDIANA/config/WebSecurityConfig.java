package com.BE.HelpDIANA.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //configure AuthenticationManager so that it knows from where to load
        //user for matching credentials
        //User BCryptPasswordEncoder

        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public HttpFirewall defaultHttpFirewall() {
        return new DefaultHttpFirewall();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // We don't need CSRF for this example
        httpSecurity.csrf().disable()
                // dont authenticate this particular request
                .authorizeRequests()
                .antMatchers("/",
                        "/api/auth/**",
                        "/api/main/home",
                        "/api/diagnose/add",
                        "/api/diagnoseTest/add",
                        "/api/diagnoseTest/ocr",
                        "/api/diagnoseTest/ocr?diagnose_id=18",
                        "/api/diagnoseTest/ocr/{diagnose_id}",
                        "/api/diagnoseTest/ocr/update/{diagnose_id}",
                        "/api/diagnoseTest/translate/{diagnose_id}",
                        "/api/diagnoseTest/translate/update/{diagnose_id}",
                        "/api/diagnoseTest/ocr/update",
                        "/api/diagnoseTest/translate",
                        "/api/diagnoseTest/translate/update",
                        "/api/diagnoseTest/delete",
                        "/api/diagnose/addbase",
                        "/api/diagnose/my",
                        "/api/diagnoseTest/highlight",
                        "/api/diagnose/ocr",
                        "/api/diagnose//highlight",
                        "/api/diagnose/upload",
                        "/api/reserve/add/clinic",
                        "/api/reserve/add/examine",
                        "/api/reserve/clinic",
                        "/api/reserve/examine",
                        "/api/reserve/update/clinic",
                        "/api/reserve/update/examine",
                        "/api/reserve/delete/clinic",
                        "/api/reserve/delete/examine",
                        "/api/myPage",
                        "/api/uploadFile").permitAll().
                // all other requests need to be authenticated
                        anyRequest().authenticated().and().
                // make sure we use stateless session; session won't be used to
                // store user's state.
                        exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // Add a filter to validate the tokens with every request
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
