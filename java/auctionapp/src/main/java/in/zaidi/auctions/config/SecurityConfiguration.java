package in.zaidi.auctions.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import in.zaidi.engineering.auctions.api.RoleType;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfiguration.class);
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/signup", "/about", "/test", "/help", "/login", "/sales","/").permitAll()
                .antMatchers("/admin/**").hasRole(RoleType.ADMIN.name()).anyRequest().authenticated().and().formLogin()
                .loginPage("/login").usernameParameter("username").passwordParameter("password").permitAll()
                .defaultSuccessUrl("/sales");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**", "/js/**", "/css/**", "/img/**");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, UserDetailsService userService) {
        try {
            auth.userDetailsService(userService);
        } catch (Exception e) {
            LOGGER.error("Error setting userService for authentication", e);
        }
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new StandardPasswordEncoder();
    }

    @Bean
    public SaltSource getSaltSource() {
        ReflectionSaltSource saltSource = new ReflectionSaltSource();
        saltSource.setUserPropertyToUse("login");
        return saltSource;
    }
}