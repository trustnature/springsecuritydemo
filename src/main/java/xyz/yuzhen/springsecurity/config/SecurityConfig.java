package xyz.yuzhen.springsecurity.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.io.PrintWriter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

   /* @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("root")
                .password("root").roles("admin");
    }
*/

   /* @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("javatest").password("123").roles("admin").build());
        manager.createUser(User.withUsername("testone").password("456").roles("admin").build());
        return manager;
    }*/

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**", "/css/**", "/images/**");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .anyRequest().authenticated()
                .and()//and ????????????????????????????????????????????????HttpSecurity???????????????????????????
                .formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/doLogin")
                .usernameParameter("name")
                .passwordParameter("passwd")
                .successForwardUrl("/success")//???????????????
                //.defaultSuccessUrl("/index")//?????????????????????
                .successHandler((req, resp, authentication) -> {
                    Object principal = authentication.getPrincipal();
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write(new ObjectMapper().writeValueAsString(principal));
                    out.flush();
                    out.close();
                })
                .failureHandler((req, resp, e) -> {
                    String emsg = "";
                    if (e instanceof LockedException) {
                        emsg = "????????????????????????????????????!";
                    } else if (e instanceof CredentialsExpiredException) {
                        emsg = "?????????????????????????????????!";
                    } else if (e instanceof AccountExpiredException) {
                        emsg = "?????????????????????????????????!";
                    } else if (e instanceof DisabledException) {
                        emsg = "????????????????????????????????????!";
                    } else if (e instanceof BadCredentialsException) {
                        emsg = "???????????????????????????????????????????????????!";
                    }
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write(emsg);
                    out.flush();
                    out.close();
                })
                .failureForwardUrl("/fail")
                .permitAll() //permitAll ???????????????????????????/????????????????????????
                .and()
                .logout()
                .logoutUrl("/logout")
                .and()
                .csrf().disable();
    }

}
