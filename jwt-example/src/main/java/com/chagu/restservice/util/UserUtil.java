package com.chagu.restservice.util;

import com.chagu.restservice.security.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

@Component
public class UserUtil {

    private final Random RANDOM = new SecureRandom();
    private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public String generatePublicId(int length) {
        return generateRandomString(length);
    }

    private String generateRandomString(int length) {
        StringBuilder returnValue = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }

    public static boolean isTokenExpired(String token) {
        Claims claims = Jwts.parser().setSigningKey(SecurityConstants.TOKEN_SECRET.getConstant()).parseClaimsJws(token)
                .getBody();
        Date tokenExpirationDate = claims.getExpiration();
        Date today = new Date();
        return tokenExpirationDate.before(today);
    }

    public static String generateEmailVerificationToken(String publicUserId) {
        return Jwts.builder().setSubject(publicUserId)
                .setExpiration(new Date(
                        System.currentTimeMillis() + Long.parseLong(SecurityConstants.EXPIRATION_TIME.getConstant())))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.TOKEN_SECRET.getConstant()).compact();
    }

}
