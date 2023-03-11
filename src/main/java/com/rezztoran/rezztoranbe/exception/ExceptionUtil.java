package com.rezztoran.rezztoranbe.exception;

import com.rezztoran.rezztoranbe.exception.BusinessException.Ex;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExceptionUtil {

  private final MessageSource messageSource;

  public BusinessException buildException(Ex e, Object... params) {
    return new BusinessException(
        messageSource.getMessage(e.getMessage(), params, LocaleContextHolder.getLocale()),
        e.getStatus());
  }
}
