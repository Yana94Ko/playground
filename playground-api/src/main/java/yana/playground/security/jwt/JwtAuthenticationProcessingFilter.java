package yana.playground.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
public class JwtAuthenticationProcessingFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtils jwtUtils;
    public JwtAuthenticationProcessingFilter(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        super(authenticationManager);
        this.jwtUtils = jwtUtils;
        /*
        * default login url이 /login 이기때문에, 변경을 원할시 생성자에서 setFilterProcessesUrl을 설정해줘야함
         * */
        setFilterProcessesUrl("/loginDo");
    }

    /**
     * 로그인 인증을 위해 request에서 받아온 정보를 UsernamePasswordAuthenticationToken에 담아줌
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginDto loginDto = new ObjectMapper().readValue(request.getInputStream(),
                    LoginDto.class);

            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(), //평문으로 주고받지 않기 위해 Header가 아닌 Parameter로 수신
                    loginDto.getPassword(),
                    new ArrayList<>()
            );

            return getAuthenticationManager().authenticate(authRequest);
        }catch (Exception e){
            log.error("request mapping 작업중 에러 발생");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 인증 성공시 JWT Token을 생성해 쿠키에 넣음
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.error("성공적으로 로그인");
        User user = (User)authResult.getPrincipal();
        String token = jwtUtils.createToken(user);

        //Cookie 생성
        Cookie cookie = new Cookie(JwtProperties.COOKIE_NAME, token);
        cookie.setMaxAge(JwtProperties.EXPIRATION_TIME);
        cookie.setPath("/");
        response.addCookie(cookie);
        response.sendRedirect("/api/members");
    }

    /**
     * 인증 실패시 log 생성 및 error 메세지 반환
     * TODO : error를 ApiExceptionHandler에서 핸들링 가능한지 테스트 해보기
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.error("unsuccessfulAuthentication failed.getLocalizedMessage(): {}", failed.getLocalizedMessage());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("code", HttpStatus.UNAUTHORIZED.value());
        body.put("error", failed.getMessage());

        new ObjectMapper().writeValue(response.getOutputStream(), body);

    }
}
