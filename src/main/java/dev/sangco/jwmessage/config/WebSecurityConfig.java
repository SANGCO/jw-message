package dev.sangco.jwmessage.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.http.HttpMethod.*;

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

        http.httpBasic()
                .and().authorizeRequests()
                .antMatchers(GET, "/api/accounts/**").hasRole("USER")
                .antMatchers(PUT, "/api/accounts/**").hasRole("USER")
                .antMatchers(DELETE, "/api/accounts/**").hasRole("USER")
//                .antMatchers(HttpMethod.GET, "/admin/test/**").hasRole("ADMIN")
                .anyRequest().permitAll()

//        .and().csrf().disable()
                // TODO disable()한게 배포시에 올라가면 안된다.

                .and().formLogin().loginPage("/accounts/login").failureUrl("/accounts/login?error=true")
                .defaultSuccessUrl("/")
                .usernameParameter("accId")
                .passwordParameter("password")

                .and().logout().logoutUrl("/accounts/logout").logoutSuccessUrl("/").invalidateHttpSession(true)

                .and().exceptionHandling().accessDeniedPage("/accounts/accessDenied");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }
}
