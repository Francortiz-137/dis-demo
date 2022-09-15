package com.disneydemo.demo.security;

import com.disneydemo.demo.model.DisneyUser;
import com.disneydemo.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
class WebSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()

                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()

                .anyRequest().authenticated()

                .and()
                .formLogin(
                        form -> form
                              //  .loginPage("/login")
                                .loginProcessingUrl("/auth/login")
                                .defaultSuccessUrl("/")
                                .usernameParameter("userName")
                                .passwordParameter("password")
                )
                .httpBasic();

        ;
                /*

                .and()
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .permitAll()
                );
                */

        /*

                //.antMatchers("/").permitAll()
                //.antMatchers("/api").permitAll()
                //.antMatchers("/auth").permitAll()
                .and()
                //.formLogin()

                //.loginPage("/auth/login")
                .formLogin(
                        form -> form
                                .loginPage("/auth/login")
                                .loginProcessingUrl("/auth/login")
                //                .defaultSuccessUrl("/users")
                                .usernameParameter("email")
                                .passwordParameter("password")
                                .permitAll()
                )
                .logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
                                .permitAll()

                )
        ;




         */
        // turn off checking for CSRF tokens
        //http.csrf().disable();
        /*
        http.logout().logoutUrl("/auth/logout");



        // if user is not authenticated, just send an authentication failure response
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if login is successful, just clear the flags asking for authentication
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // if login fails, just send an authentication failure response
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if logout is successful, just send a success response
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());


         */
        return http.build();
    }
/*
    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }


 */
    /*
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User
                .withUsername("user")
                .password(passwordEncoder().encode("password2"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

     */
}


@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {
    @Autowired
    UserService disneyUserService;


    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(inputName-> {
            DisneyUser disneyUser = disneyUserService.findByUserName(inputName);
            if (disneyUser != null) {
                return new User(disneyUser.getUserName(), disneyUser.getPassword(),
                        AuthorityUtils.createAuthorityList("USER"));
            } else {
                throw new UsernameNotFoundException("Unknown user: " + inputName);
            }
        });
    }
}

