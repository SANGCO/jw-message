package dev.sangco.jwmessage.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@Profile("dev")
public class DevWebSecurityConfig extends WebSecurityConfigurerAdapter {

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
                .and().csrf().disable()
                .authorizeRequests()
                .antMatchers(GET, "/api/companies/**").hasRole("ADMIN")
//                .antMatchers(PUT, "/api/companies/**").hasRole("ADMIN")
//                .antMatchers(DELETE, "/api/companies/**").hasRole("ADMIN")
                .antMatchers(GET, "/companies/**").hasRole("ADMIN")
//                .antMatchers(PUT, "/companies/**").hasRole("ADMIN")
//                .antMatchers(DELETE, "/companies/**").hasRole("ADMIN")
                .antMatchers(GET, "/message/**").hasRole("ADMIN")
                .antMatchers(GET, "/storage/**").hasRole("ADMIN")

                .anyRequest().permitAll()

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
