package fr.polytech.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
public class CovidAlertSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;
    @Value("${server.port.http}")
    private int serverPortHttp;
    @Value("${server.port}")
    private int serverPortHttps;

    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl token = new JdbcTokenRepositoryImpl();
        token.setDataSource(dataSource);
        return token;
    }

    protected void configure(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .headers(headers ->
                        headers.httpStrictTransportSecurity(hsts ->
                                hsts.includeSubDomains(true)
                                        .preload(true)
                                        .maxAgeInSeconds(31536000)))
                .authorizeRequests()
                .antMatchers("/login*").permitAll()
                .antMatchers("/register*").permitAll()
                .antMatchers("/doRegister").permitAll()
                .antMatchers("/static/css/**", "/static/js/**",
                        "/images/**").permitAll()
                .antMatchers("/index*").permitAll()
                .antMatchers("/userConfirm").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login")
                .loginProcessingUrl("/doLogin")
                .failureUrl("/login?error=true")
                .permitAll().defaultSuccessUrl("/", true)
                .and()
                .rememberMe().key("SecretKey")
                .tokenRepository(tokenRepository())
                .and()
                .logout().logoutSuccessUrl("/login?logout=true")
                .logoutRequestMatcher(
                        new AntPathRequestMatcher("/doLogout", "GET")
                )
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .passwordEncoder(passwordEncoder())
                .dataSource(dataSource);
//                .withUser("admin")
//                .password(passwordEncoder().encode("adminadmin"))
//                .disabled(false)
//                .roles("USER", "ADMIN");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
