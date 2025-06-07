package com.codewithsidd.taskmanager.util;

import java.text.ParseException;
import java.util.Map;
import com.nimbusds.jwt.SignedJWT;

public class JwtUtils {

    public static Map<String, Object> decodeToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getClaims();
        } catch (ParseException e) {
            throw new RuntimeException("Invalid token", e);
        }
    }

}
