package com.ashenone.AshenOne.config;

import com.ashenone.AshenOne.domain.User;
import com.ashenone.AshenOne.repo.UserRepo;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.time.LocalDateTime;

@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "/login**", "/js/**", "/error**").permitAll()
                .anyRequest().authenticated()
                .and().logout().logoutSuccessUrl("/").permitAll()
                .and()
                .csrf().disable();
    }

    @Bean
    public PrincipalExtractor principalExtractor(UserRepo userRepo)
    {
        return map -> {

            String id = (String) map.get("sub");

            User user = userRepo.findById(id).orElseGet(() -> {

                User authUser = new User();

                authUser.setId(id);
                authUser.setName((String) map.get("name"));
                authUser.setEmail((String) map.get("email"));
                authUser.setGender((String) map.get("gender"));
                authUser.setLocale((String) map.get("locale"));
                authUser.setPicture((String) map.get("picture"));

                return authUser;
            });

            user.setLastVisit(LocalDateTime.now());

            return userRepo.save(user);
        };
    }
}
