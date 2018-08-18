package dev.sangco.jwmessage.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;

import javax.sql.DataSource;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    public UserDetailsService userDetailsService;

    @Qualifier("dataSource")
    @Autowired
    public DataSource dataSource;

    @Autowired
    public MessageSourceAccessor msa;

    @Bean
    public PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices() {
        PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices
                = new PersistentTokenBasedRememberMeServices(msa.getMessage("REMEMBER_ME_KEY"), userDetailsService, jdbcTokenRepository());
        return persistentTokenBasedRememberMeServices;
    }

    @Bean
    public JdbcTokenRepositoryImpl jdbcTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setCreateTableOnStartup(false);
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and().csrf().disable()
                .authorizeRequests()
                .antMatchers(GET, "/api/companies/**").hasRole("ADMIN")
                .antMatchers(POST, "/api/companies/**").hasRole("ADMIN")
                .antMatchers(PUT, "/api/companies/**").hasRole("ADMIN")
                .antMatchers(DELETE, "/api/companies/**").hasRole("ADMIN")
                .antMatchers(GET, "/companies/**").hasRole("ADMIN")
                .antMatchers(GET, "/message/**").hasRole("ADMIN")
                .antMatchers(GET, "/storage/**").hasRole("ADMIN")
                .anyRequest().permitAll()

                .and().formLogin().loginPage("/accounts/login").failureUrl("/accounts/login?error=true")
                .defaultSuccessUrl("/")
                .usernameParameter("accId")
                .passwordParameter("password")

                .and().logout().logoutUrl("/accounts/logout").logoutSuccessUrl("/").invalidateHttpSession(true)
                .and().exceptionHandling().accessDeniedPage("/accounts/accessDenied")
                .and().rememberMe().key(msa.getMessage("REMEMBER_ME_KEY")).rememberMeServices(persistentTokenBasedRememberMeServices());
    }
}
