package com.rezztoran.rezztoranbe.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.rezztoran.rezztoranbe.model.User;
import com.rezztoran.rezztoranbe.service.TokenService;
import com.rezztoran.rezztoranbe.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {


    private final UserService userService;
    @Value("${jwt-variables.KEY}")
    private String KEY;

    @Value("${jwt-variables.ISSUER}")
    private String ISSUER;

    @Value("${jwt-variables.EXPIRES_ACCESS_TOKEN_MINUTE}")
    private Integer EXPIRES_ACCESS_TOKEN_MINUTE;

    public TokenServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String generateToken(Authentication auth) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = userService.findUserByUsername(userDetails.getUsername());
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(
                        new Date(System.currentTimeMillis() + (EXPIRES_ACCESS_TOKEN_MINUTE * 60 * 1000)))
                .withIssuer(ISSUER)
                .withClaim(
                        "passwordChangeVersion",
                        user.getPasswordChangeVersion()) // Add password change version to payload
                .sign(Algorithm.HMAC256(KEY.getBytes()));
    }

    @Override
    public DecodedJWT verifyJWT(String token) {
        Algorithm algorithm = Algorithm.HMAC256(KEY.getBytes(StandardCharsets.UTF_8));
        JWTVerifier verifier =
                JWT.require(algorithm).acceptExpiresAt(EXPIRES_ACCESS_TOKEN_MINUTE * 60).build();
        try {
            return verifier.verify(token);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage().toString());
        }
    }
}



