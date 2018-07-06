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
@Profile("default")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Qualifier("dataSource")
    @Autowired
    DataSource dataSource;

    public static final String REMEMBER_ME_KEY = "jungwon";

    @Bean
    public PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices(){
        PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices
                = new PersistentTokenBasedRememberMeServices(REMEMBER_ME_KEY, userDetailsService, jdbcTokenRepository());
        return persistentTokenBasedRememberMeServices;
    }

    @Bean
    public JdbcTokenRepositoryImpl jdbcTokenRepository(){
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
    protected void configure(HttpSecurity http) throws Exception {

        http.httpBasic()
                .and().authorizeRequests()
                .antMatchers(GET, "/api/accounts/**").hasRole("USER")
                .antMatchers(PUT, "/api/accounts/**").hasRole("USER")
                .antMatchers(DELETE, "/api/accounts/**").hasRole("USER")
//                .antMatchers(HttpMethod.GET, "/admin/test/**").hasRole("ADMIN")
                .anyRequest().permitAll()

                .and().formLogin().loginPage("/accounts/login").failureUrl("/accounts/login?error=true")
                .defaultSuccessUrl("/")
                .usernameParameter("accId")
                .passwordParameter("password")

                .and().logout().logoutUrl("/accounts/logout").logoutSuccessUrl("/").invalidateHttpSession(true)
                .and().exceptionHandling().accessDeniedPage("/accounts/accessDenied")
                .and().rememberMe().key(REMEMBER_ME_KEY).rememberMeServices(persistentTokenBasedRememberMeServices());
                // TODO 실서버에 리멤버미 관련 테이블 추가

        // TODO 중복 로그인 처리
//        http.sessionManagement()
//                .invalidSessionUrl("/spring/error?error_code=1")
//                .sessionAuthenticationErrorUrl("/spring/error?error_code=2")
//                .maximumSessions(1)
//                .expiredUrl("/spring/error?error_code=3")
//                .maxSessionsPreventsLogin(true)
//                .sessionRegistry(sessionRegistry());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }
}
