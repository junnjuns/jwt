package com.example.jwt.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.jwt.auth.PrincipalDetails;
import com.example.jwt.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;

//스프링 시큐리티에서 UsernamePasswordAuthenticationFilter 가 있음.
//login 요청해서 username, password를 전송하면(post)
//UsernamePasswordAuthenticationFilter 가 동작을 한다.
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    //login 요청을 하면 로그인 시도를 위해서 자동으로 실행되는 함수,
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        System.out.println("JwtAuthenticationFilter : 로그인 시도중");
        //1.username, password 받아서
        try {
//            BufferedReader br =request.getReader();
//
//            String input = null;
//            while((input = br.readLine())!=null){
//                System.out.println(input);
//            }
            ObjectMapper om = new ObjectMapper();
            User user = om.readValue(request.getInputStream(), User.class);
            System.out.println(user);   //콘솔 확인용

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

            //PrincipalDetailsService의 loadUserByUsername()함수가 실행된 후 정상이면 authentication이 리턴됨.
            //DB에 있는 username과 password가 일치한다는 의미
            Authentication authentication = authenticationManager.authenticate(authenticationToken);


            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal(); //
            System.out.println("로그인 완료됨: " + principalDetails.getUser().getUsername()); // 로그인 정상적으로 되었다는 뜻

            //authentication 객체가 session 영역에 저장을 해야하고 그 방법이 return
            //권한 처리 때문에 session에 넣어준다.
            return authentication;
        } catch (IOException e) {
            e.printStackTrace();
        }
        //2. 정상인지 로그인 시도를 해본다. authenticationManager로 로그인 시도를 하면
        //                              PrincipalDetailsService가 호출 -> loadUserByUsername() 함수 실행됨.
        //3. PrincipalDetails를 세션에 담고(권한 관리를 위해서)
        //4. JWT토큰을 만들어서 응답해주면 된다.
        return null;
    }

    //attemptAuthentication 실행 후 인증이 정상적으로 되었으면 함수 실행
    //JWT 토큰을 만들어서 request 요청한 사용자에게 JWT토큰 response해주면 된다.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        PrincipalDetails principalDetailis = (PrincipalDetails) authResult.getPrincipal();

        String jwtToken = JWT.create()  //토큰 생성(?)
                .withSubject(principalDetailis.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME))
                .withClaim("id", principalDetailis.getUser().getId())
                .withClaim("username", principalDetailis.getUser().getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX+jwtToken);
    }
}
