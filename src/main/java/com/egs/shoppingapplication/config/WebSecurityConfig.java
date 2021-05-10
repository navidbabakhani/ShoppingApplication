package com.egs.shoppingapplication.config;

import com.egs.shoppingapplication.entity.enums.UserRole;
import com.egs.shoppingapplication.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserAuthService userAuthService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userAuthService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    /**
     * This configuration allows the application urls to be accessible by different roles.
     * For example, currently, the ADMIN role can have crud operation on users and categories and products.
     * But USER role can only do login/logout/sign-up and search for products
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        http.csrf().disable();

        // Requires login with role ADMIN_ROLE or USER_ROLE.
        // If not, it will redirect to /admin/login.
//        http.authorizeRequests().antMatchers( "/users")//
//                .access("hasAnyRole('ADMIN_ROLE', 'USER_ROLE')");

        // Pages only for Admins
//        http.authorizeRequests().antMatchers("/users").access("hasRole('ADMIN_ROLE')");

        // When user login, role XX.
        // But access to the page requires the YY role,
        // An AccessDeniedException will be thrown.
//        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

        // Configuration for Login Form.
//        http.authorizeRequests().and().formLogin()
//                .loginProcessingUrl("/j_spring_security_check") // Submit URL
//                .defaultSuccessUrl("/admin/accountInfo")
//                .failureUrl("/admin/login?error=true")
//                .usernameParameter("username")
//                .passwordParameter("password")

        // Configuration for the Logout page.
        // (After logout, go to home page)
//                .and().logout().logoutUrl("/admin/logout").logoutSuccessUrl("/");

//        http
//                .authorizeRequests()
//                .antMatchers("/sign-up/**", "/sign-in/**")
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/sign-in")
//                .permitAll()
//                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/");
//        http.authorizeRequests().anyRequest().authenticated();


//        http.authorizeRequests().anyRequest().permitAll();
        http
                .httpBasic() // This basic Http is added for the usage of Postman, (Authentication is Postman didn't work without this)
                .and()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/sign-up").permitAll()
                .antMatchers("/products/add/**").hasRole(UserRole.ADMIN.name())
                .antMatchers("/products/delete/**").hasRole(UserRole.ADMIN.name())
                .antMatchers("/users/**").hasRole(UserRole.ADMIN.name())
                .antMatchers("/categories/**").hasRole(UserRole.ADMIN.name())
                .antMatchers("/**").authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/authenticate")
                .and()
                .logout()
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/login")
                .permitAll();

    }
}