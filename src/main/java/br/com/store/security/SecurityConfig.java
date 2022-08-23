package br.com.store.security;

import br.com.store.entites.Client;
import br.com.store.service.ClientService;
import br.com.store.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Arrays;
import java.util.Collections;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    OperatorService operatorService;

    @Autowired
    ClientService clientService;

    @Autowired
    private JwtService jwtService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OncePerRequestFilter jwtFilter(){
        return new JwtAuthFilter(jwtService, clientService);
    }

    @Bean
    public OncePerRequestFilter jwtFilterOperator(){
        return new JwtAuthFilter(jwtService, operatorService);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(clientService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable().authorizeRequests().
                antMatchers("/client/create")
                .permitAll()
                .antMatchers("/client/findById{id}")
                .authenticated()
                .antMatchers("/client/findFilter")
                .authenticated()
                .antMatchers("client/login")
                .permitAll()
                .antMatchers("/requests/sale")
                .authenticated()
                .antMatchers("/request/findAll")
                .authenticated()
                .antMatchers("/requests/findClientPurchases")
                .authenticated()
                .antMatchers("/product/allProducts")
                .permitAll()
                .antMatchers("/product/findByProductId{id}")
                .permitAll()
                .antMatchers("/product/create")
                .authenticated()
                .antMatchers("/product/delete{id}")
                .authenticated()
                .antMatchers("/product/addStock")
                .authenticated()
                .antMatchers("/product/attPrice")
                .authenticated()
                .antMatchers("/operator/create")
                .permitAll()
                .antMatchers("operator/delete")
                .authenticated()
                .antMatchers("/operator/updateResponsibility")
                .authenticated()
                .antMatchers("/operator/login")
                .permitAll()
                .antMatchers("/operator/findOperators")
                .authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtFilterOperator(), UsernamePasswordAuthenticationFilter.class);
    }
}
