package io.jongyun.userservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jongyun.userservice.dto.RequestLogin;
import io.jongyun.userservice.dto.UserDto;
import io.jongyun.userservice.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/** @author jongyunha created on 22. 3. 24. */
@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private UserService userService;
  private Environment env;

  public AuthenticationFilter(
      AuthenticationManager authenticationManager, UserService userService, Environment env) {
    super.setAuthenticationManager(authenticationManager);
    this.userService = userService;
    this.env = env;
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    try {
      RequestLogin creds =
          new ObjectMapper().readValue(request.getInputStream(), RequestLogin.class);
      return getAuthenticationManager()
          .authenticate(
              new UsernamePasswordAuthenticationToken(
                  creds.getEmail(), creds.getPassword(), new ArrayList<>()));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication authResult)
      throws IOException, ServletException {
    log.info(((User) authResult.getPrincipal()).getUsername());
    String username = ((User) authResult.getPrincipal()).getUsername();
    UserDto userDetails = userService.getUserDetailsByEmail(username);

    String token =
        Jwts.builder()
            .setSubject(userDetails.getUserId())
            .setExpiration(
                new Date(
                    System.currentTimeMillis()
                        + Long.parseLong(env.getProperty("token.expiration_time"))))
            .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
            .compact();

    response.addHeader("token", token);
    response.addHeader("userId", userDetails.getUserId());
  }
}
