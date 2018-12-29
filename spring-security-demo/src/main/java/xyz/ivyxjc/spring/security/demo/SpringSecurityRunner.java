package xyz.ivyxjc.spring.security.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.net.URL;

/**
 * @author ivyxjc
 * @date 12/29/2018 5:18 PM
 */
@SpringBootApplication
@EnableWebSecurity
public class SpringSecurityRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("customUserDetail")
    private UserDetailsService userDetailService;

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityRunner.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/*")
                        .allowedOrigins("http://127.0.0.1:4321")
                        .allowCredentials(true)
                        .allowedMethods("GET", "POST", "OPTIONS");
                registry.addMapping("/api/logout")
                        .allowedOrigins("http://127.0.0.1:4321")
                        .allowCredentials(true)
                        .allowedMethods("GET", "POST", "OPTIONS");
                registry.addMapping("/api/hello")
                        .allowedOrigins("http://127.0.0.1:4321")
                        .allowCredentials(true)
                        .allowedMethods("GET", "POST", "OPTIONS");
            }
        };
    }

    @Bean
    public WebSecurityConfigurerAdapter securityConfigurer() {
        return new WebSecurityConfigurerAdapter() {

            @Override
            protected void configure(HttpSecurity http) throws Exception {
                http.cors().
                        and()
                        .authorizeRequests()
                        .antMatchers(HttpMethod.OPTIONS, "/api/**").permitAll()
                        .antMatchers("/home").permitAll()
                        .anyRequest()
                        .fullyAuthenticated()
                        .and()
                        .httpBasic()
                        .and()
                        .csrf()
                        .and()
                        .logout().logoutRequestMatcher(new AntPathRequestMatcher("/api/logout"));
            }

            @Override
            protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                DaoAuthenticationProvider daoAuthenticationProvider =
                        new DaoAuthenticationProvider();
                daoAuthenticationProvider.setUserDetailsService(userDetailsService());
                daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
                auth.authenticationProvider(daoAuthenticationProvider);
            }

            @Override
            protected UserDetailsService userDetailsService() {
                return userDetailService;
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Component
    private class DBInitilizeRunner implements ApplicationRunner {
        @Autowired
        private NamedParameterJdbcTemplate jdbcTemplate;

        @Autowired
        private DataSource dataSource;

        @Override
        public void run(ApplicationArguments args) throws Exception {
            //jdbcTemplate.
            URL dml = this.getClass().getResource("/db/init_structure.sql");
            ScriptUtils.executeSqlScript(dataSource.getConnection(), new FileUrlResource(dml));
            URL ddl = this.getClass().getResource("/db/init_data.ddl");
            ScriptUtils.executeSqlScript(dataSource.getConnection(), new FileUrlResource(ddl));
        }
    }

    @Component
    private class passwordEncoderRunner implements ApplicationRunner {
        @Autowired
        private PasswordEncoder passwordEncoder;

        @Override
        public void run(ApplicationArguments args) throws Exception {
            System.out.println("+++++++++++++++++");
            String res = passwordEncoder.encode("123");
            boolean match = passwordEncoder.matches("123",
                    "$2a$10$P1XHnJSRQNL/1vFE.aTWrOo89/EH1kSyDONSgb955OghBuDG6MUDa");
            System.out.println(res);
            System.out.println(match);
        }
    }
}
