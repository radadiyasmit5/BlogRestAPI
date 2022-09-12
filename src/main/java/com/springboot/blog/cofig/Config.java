package com.springboot.blog.cofig;

import com.springboot.blog.security.CustomeuserDetails;
import com.springboot.blog.security.Jwtauthenticationfilter;
import com.springboot.blog.utils.Authenticationentrypoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Config extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private Authenticationentrypoint authenticationentrypoint;

    @Bean
    public Jwtauthenticationfilter jwtauthenticationfilter(){
        return  new Jwtauthenticationfilter();
    }
    //authorization
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationentrypoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET ,"/api/**").hasRole("USER")
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/v3/api-docs/**").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/swagger-ui.html/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .anyRequest()
                .authenticated();

        http.addFilterBefore(jwtauthenticationfilter(), UsernamePasswordAuthenticationFilter.class);

    }

    //for inmeory users
//    @Bean
//    @Override
//    protected UserDetailsService userDetailsService() {
//        UserDetails smit= User.builder().username("smit").password(passwordEncoder().encode("smit") ).roles("USER").build();
//        UserDetails dhruva= User.builder().username("dhruva").password(passwordEncoder().encode("password") ).roles("ADMIN").build();
//        return new InMemoryUserDetailsManager(smit,dhruva);
//
//    }

    @Autowired
    private CustomeuserDetails customeuserDetails;



    //authentication
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
           auth.userDetailsService(customeuserDetails).passwordEncoder(passwordEncoder());
    }



    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}


