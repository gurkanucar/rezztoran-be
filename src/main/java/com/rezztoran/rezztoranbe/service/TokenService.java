package com.rezztoran.rezztoranbe.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.Authentication;
/**
 * The interface Token service.
 */
public interface TokenService {

  /**
   * Generate token string.
   *
   * @param auth the auth
   * @return the string
   */
String generateToken(Authentication auth);

  /**
   * Verify jwt decoded jwt.
   *
   * @param token the token
   * @return the decoded jwt
   */
  DecodedJWT verifyJWT(String token);
}
