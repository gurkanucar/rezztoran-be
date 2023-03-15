package com.rezztoran.rezztoranbe.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rezztoran.rezztoranbe.config.MessageConfig;
import com.rezztoran.rezztoranbe.exception.BusinessException.Ex;
import com.rezztoran.rezztoranbe.response.ApiResponse;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * The type Jwt authentication entry point.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final ObjectMapper objectMapper;
  private final MessageSource messageSource;
  private final MessageConfig localeResolver;

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException, ServletException {
    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response
        .getOutputStream()
        .println(
            objectMapper.writeValueAsString(
                ApiResponse.builder()
                    .error(
                        messageSource.getMessage(
                            Ex.FORBIDDEN_EXCEPTION.getMessage(),
                            new String[] {
                              localeResolver.localeResolver().resolveLocale(request).getLanguage()
                            },
                            localeResolver.localeResolver().resolveLocale(request)),
                        HttpStatus.FORBIDDEN)
                    .build()
                    .getBody()));
  }
}
