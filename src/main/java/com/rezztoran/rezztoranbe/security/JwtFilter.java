package com.rezztoran.rezztoranbe.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rezztoran.rezztoranbe.exception.BusinessException.Ex;
import com.rezztoran.rezztoranbe.exception.ExceptionUtil;
import com.rezztoran.rezztoranbe.model.User;
import com.rezztoran.rezztoranbe.service.TokenService;
import com.rezztoran.rezztoranbe.service.UserDetailsServiceImpl;
import com.rezztoran.rezztoranbe.service.UserService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/** The type Jwt filter. */
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

  private final TokenService tokenService;
  private final UserService userService;
  private final UserDetailsServiceImpl userDetailsService;
  private final ExceptionUtil exceptionUtil;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String header = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (header == null || !header.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }
    String token = header.substring(7);
    String username;
    DecodedJWT decodedJWT;
    Integer passwordChangeVersion;
    try {
      decodedJWT = tokenService.verifyJWT(token);
      username = decodedJWT.getSubject();
      passwordChangeVersion = decodedJWT.getClaim("passwordChangeVersion").asInt();
    } catch (Exception e) {
      sendError(response, e);
      return;
    }
    if (username == null) {
      filterChain.doFilter(request, response);
      return;
    }

    User user = userService.findUserByUsername(username);
    if (user == null || !Objects.equals(user.getPasswordChangeVersion(), passwordChangeVersion)) {
      sendError(response, exceptionUtil.buildException(Ex.TOKEN_IS_NOT_VALID_EXCEPTION));
      return;
    }

    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    filterChain.doFilter(request, response);
  }

  private void sendError(HttpServletResponse res, Exception e) throws IOException {
    res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    res.setContentType("application/json");
    PrintWriter out = res.getWriter();
    Map<String, String> errors = new HashMap<>();
    ObjectMapper mapper = new ObjectMapper();
    errors.put("error", e.getMessage());
    out.println(mapper.writeValueAsString(errors));
    out.flush();
  }
}
