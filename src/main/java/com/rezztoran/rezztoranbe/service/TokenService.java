package com.rezztoran.rezztoranbe.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.Authentication;
public interface TokenService {

  String generateToken(Authentication auth);

  DecodedJWT verifyJWT(String token);
}
