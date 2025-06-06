package com.lion.demo.config;

import com.lion.demo.security.JwtRequestFilter;
import com.lion.demo.security.MyOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
public class SecurityConfig {

    @Autowired private AuthenticationFailureHandler failureHandler;
    @Autowired private MyOAuth2UserService myOAuth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(auth -> auth.disable())  // csrf 방어기능 비활성
                .headers( x -> x.frameOptions(y -> y.disable())) // H2 디비 쓰려고
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(//"/book/**","cart/**",
                                "/book/list", "/book/detail/**", "/bookEs/list", "/bookEs/detail/**", "/misc/**", "/actuator/**",
                                "/websocket/**", "/echo", "/personal", "/restaurant/**",
                                "/mall/list", "/mall/detail"
                                ,"/user/register", "/h2-console", "/demo/**"
                                , "/img/**", "/js/**", "/css/**", "/error/**"
                                , "/csvTest/**").permitAll()
                        .requestMatchers( "/book/insert", "/book/yes24", "/bookEs/yes24",
                                "/order/listAll"
                                ,"/order/bookStat", "/user/delete", "/user/list").hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(auth -> auth
                        .loginPage("/user/login") // login form
                        .loginProcessingUrl("/user/login") // 스프링이 낚아챔, UserDetailService 구현 객체에서 처리해줘야함
                        .usernameParameter("uid").passwordParameter("pwd")
                        .defaultSuccessUrl("/user/loginSuccess", true) // 로그인후 해야할 일
                        .failureHandler(failureHandler)
                        .permitAll()
                )
                .logout( auth -> auth
                        .logoutUrl("/user/logout")
                        .invalidateHttpSession(true) // 로그아웃시 세션 삭제
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/user/login")
                )
                .oauth2Login( auth -> auth
                        .loginPage("/user/login")
                        // 1. 코드받기(인증)  2.액세스 토큰(권한)  3.사용자 정보 획득
                        // 4. 3에서 받은 정보를 토대로 DB에 없으면 가입시켜줌
                        // 5. provider가 제공하는 정보
                        .userInfoEndpoint(user -> user.userService(myOAuth2UserService))
                        .defaultSuccessUrl("/user/loginSuccess", true) 
                )
        ;
        return http.build();
    }

    // JWT 필터 등록
    @Bean
    public JwtRequestFilter jwtRequestFilter() {
        return new JwtRequestFilter();
    }

    // Authentication Manager 빈 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
