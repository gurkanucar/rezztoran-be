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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/** The type Jwt access denied handler. */
@Component
@RequiredArgsConstructor
public class JWTAccessDeniedHandler implements AccessDeniedHandler {

  private final ObjectMapper objectMapper;
  private final MessageSource messageSource;
  private final MessageConfig localeResolver;

  @Override
  public void handle(
      HttpServletRequest request,
      HttpServletResponse response,
      AccessDeniedException accessDeniedException)
      throws IOException, ServletException {
    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
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
