package com.rezztoran.rezztoranbe.aop;

import com.rezztoran.rezztoranbe.exception.BusinessException.Ex;
import com.rezztoran.rezztoranbe.exception.ExceptionUtil;
import com.rezztoran.rezztoranbe.service.AuthService;
import java.util.Objects;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthorizedUserAspect {

  private final AuthService authService;
  private final ExceptionUtil exceptionUtil;

  public AuthorizedUserAspect(AuthService authService, ExceptionUtil exceptionUtil) {
    this.authService = authService;
    this.exceptionUtil = exceptionUtil;
  }

  @Around("@annotation(AuthorizedUser)")
  public Object validateUser(ProceedingJoinPoint joinPoint) throws Throwable {
    var authUser = authService.getAuthenticatedUser();
    if (authUser.isEmpty()) {
      return joinPoint.proceed();
    }
    var authUserId = authUser.get().getId();
    var args = joinPoint.getArgs();
    var userId = Long.parseLong(Objects.requireNonNull(extractUserId(args)));
    if (!authUserId.equals(userId)) {
      throw exceptionUtil.buildException(Ex.FORBIDDEN_EXCEPTION);
    }
    return joinPoint.proceed();
  }

  private String extractUserId(Object[] args) {
    for (Object arg : args) {
      if (arg == null) {
        continue;
      }

      var userId = extractUserIdFromObject(arg);
      if (userId != null) {
        return userId;
      }
    }
    return null;
  }

  private String extractUserIdFromObject(Object object) {
    try {
      var userIdField = object.getClass().getDeclaredField("userId");
      userIdField.setAccessible(true);
      return userIdField.get(object).toString();
    } catch (NoSuchFieldException | IllegalAccessException e) {
      // ignore and continue
    }

    var userObject = extractNestedUserObject(object);
    if (userObject != null) {
      return extractUserIdFromObject(userObject);
    }

    return null;
  }

  private Object extractNestedUserObject(Object object) {
    try {
      var userField = object.getClass().getDeclaredField("user");
      userField.setAccessible(true);
      return userField.get(object);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      // ignore and continue
    }

    try {
      var userIdField = object.getClass().getDeclaredField("userId");
      userIdField.setAccessible(true);
      return userIdField.get(object);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      // ignore and continue
    }

    return null;
  }
}
