package com.rezztoran.rezztoranbe.config;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

@Configuration
public class MessageConfig implements WebMvcConfigurer {
  @Bean
  public AcceptHeaderLocaleResolver localeResolver() {
    AcceptHeaderLocaleResolver localeResolver =
        new AcceptHeaderLocaleResolver() {
          @Override
          public Locale resolveLocale(HttpServletRequest request) {
            String locale = request.getParameter("lang");
            return locale != null
                ? org.springframework.util.StringUtils.parseLocaleString(locale)
                : super.resolveLocale(request);
          }
        };

    localeResolver.setDefaultLocale(LocaleContextHolder.getLocale());
    return localeResolver;
  }
}
