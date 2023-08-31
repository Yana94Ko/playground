package yana.playground.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import yana.playground.member.service.MemberService;
import yana.playground.security.jwt.CustomAccessDeniedHandler;
import yana.playground.security.jwt.JwtAuthenticationProcessingFilter;
import yana.playground.security.jwt.JwtAuthorizationFilter;
import yana.playground.security.jwt.JwtProperties;
import yana.playground.security.jwt.JwtUtils;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfiguration {
    private final MemberService memberService;
    private final JwtUtils jwtUtils;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                //csrf : Cross site Request forgery를  default로 방어하는 설정. rest-api의 경우 해제하여도 좋다.
                .csrf(AbstractHttpConfigurer::disable)
                //authorizeHttpRequests : 어떤 url에 어떤 사용자를 접근하게 해줄건지 설정
                .authorizeHttpRequests((authorizeRequests) -> {authorizeRequests
                    //permitAll : 모두 접근 가능하도록
                            .requestMatchers("/").permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/members").permitAll()
                    //hasRole : requestMatchers에 작성된 경로에 대해서는 해당 권한이 있다면 접근 가능하도록 인가
                            .requestMatchers(HttpMethod.GET, "/api/admin").hasRole("ADMIN")
                    //hasAnyRole : requestMatchers에 작성된 경로에 대해서는 나열된 권한중 어느 하나라도 포함된다면 접근 가능하도록 인가
//                    .requestMatchers("/api/master/**")
//                            .hasAnyRole("ADMIN", "MASTER")
                    //anyRequests : 상기에 명시된 경로 외 다른 모든 경로에 대한 설정시 사용
                    //authenticated : requestMatchers에 작성된 경로에 대해서는 인증된 사용자만 접근 가능하도록 인가
                            .anyRequest().authenticated();
                })
                //formLogin : 웹 애플리케이션에서 사용자의 인증을 처리하기 위해 폼 기반의 로그인 페이지를 제공
                .formLogin(AbstractHttpConfigurer::disable)
//                .formLogin((formLogin) -> formLogin
//                        //loginPag정e : 해당 경로를 로그인 폼 페이지로 지정
//                        //loginProcessingUrl : 로그인을 진행할 경로 지정
//                        .loginProcessingUrl("/loginDo")
//                        //defaultSuccessUrl : 로그인 성공 후 이동 페이지
//                        .defaultSuccessUrl("/api/members")
//                        //failureUrl : 로그인 실패 후 이동 페이지
//                        .failureUrl("/api/members/login?error=true"))
                .httpBasic(AbstractHttpConfigurer::disable)//AbstractHttpConfigurer::disable
                .sessionManagement((sessionManagement) -> {sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .addFilterBefore(
                    new JwtAuthenticationProcessingFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class))
                            , jwtUtils),
                    UsernamePasswordAuthenticationFilter.class
                ).addFilterBefore(
                        new JwtAuthorizationFilter(memberService, jwtUtils),
                        BasicAuthenticationFilter.class
                )
                .exceptionHandling((exceptionHandling)->exceptionHandling
                        .authenticationEntryPoint(authenticationEntryPoint) //customEntryPoint
                        .accessDeniedHandler(accessDeniedHandler)
                )
//                //rememberMe : JSESSIONID이 만료되거나 쿠키가 없을 지라도 어플리케이션이 사용자를 기억하는 기능
//                .rememberMe(withDefaults())
                .logout(logout -> logout
                        // .logoutUrl("/logout") // post 방식으로만 동작
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // get 방식으로도 동작
                        .logoutSuccessUrl("/")
                        .deleteCookies(JwtProperties.COOKIE_NAME)
                        .invalidateHttpSession(true)
                )
                //getOrBuild : 기존의 .build()와 달리, 이미 빌드된 경우 지정한 구성으로 인스턴스를 반환하고 그렇지 않은 경우 빌드함.
                .getOrBuild();
    }
}
