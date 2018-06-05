package dev.sangco.jwmessage.config;

import dev.sangco.jwmessage.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.httpBasic();

        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/accounts/**").hasRole("USER")
                .antMatchers(HttpMethod.PUT, "/api/accounts/**").hasRole("USER")
                .antMatchers(HttpMethod.DELETE, "/api/accounts/**").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/admin/test/**").hasRole("ADMIN")
                .anyRequest().permitAll();

        http.csrf().disable();
        // TODO disable()한게 배포시에 올라가면 안된다.

        http.formLogin().loginPage("/accounts/login")
                .usernameParameter("accountId")
                .passwordParameter("password");

        http.exceptionHandling().accessDeniedPage("/accessDenied");



    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }
}
