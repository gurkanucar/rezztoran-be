package com.rezztoran.rezztoranbe.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExceptionUtil {

  private final MessageSource messageSource;

  public BusinessException buildException(BusinessException.Exception e, Object... params) {
    return new BusinessException(
        messageSource.getMessage(e.getMessage(), params, LocaleContextHolder.getLocale()),
        e.getStatus());
  }
}
