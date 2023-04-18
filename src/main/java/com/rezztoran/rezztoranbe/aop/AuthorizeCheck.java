package com.rezztoran.rezztoranbe.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** The interface Authorize check. */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthorizeCheck {
  /**
   * Field string.
   *
   * @return the string
   */
  String field();
  /**
   * Except roles string [ ].
   *
   * @return the string [ ]
   */
  String[] exceptRoles() default {};
}
