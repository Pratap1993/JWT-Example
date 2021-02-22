package com.chagu.restservice.security;

import com.chagu.restservice.SpringApplicationContext;
import com.chagu.restservice.dto.UserDto;
import com.chagu.restservice.model.request.UserLoginRequest;
import com.chagu.restservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @NonNull
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            UserLoginRequest credentials = new ObjectMapper().readValue(request.getInputStream(),
                    UserLoginRequest.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getEmail(),
                    credentials.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) {
        String userName = ((User) authResult.getPrincipal()).getUsername();

        String token = Jwts.builder().setSubject(userName)
                .setExpiration(new Date(
                        System.currentTimeMillis() + Long.parseLong(SecurityConstants.EXPIRATION_TIME.getConstant())))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.TOKEN_SECRET.getConstant()).compact();

        UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");
        UserDto userDto = userService.getUserDtoByEmail(userName);

        response.addHeader(SecurityConstants.HEADER_STRING.getConstant(),
                SecurityConstants.TOKEN_PREFIX.getConstant() + token);
        response.addHeader("UserID", userDto.getUserId());
    }

}
