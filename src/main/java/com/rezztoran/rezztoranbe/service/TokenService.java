package com.rezztoran.rezztoranbe.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.rezztoran.rezztoranbe.model.User;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


public interface TokenService {

    String generateToken(Authentication auth);

    DecodedJWT verifyJWT(String token);

}
