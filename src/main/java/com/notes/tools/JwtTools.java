package com.notes.tools;

import com.notes.domain.UserDomain;
import com.notes.response.Response;
import com.notes.response.ResponseHandler;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
public class JwtTools implements ResponseHandler {

    @Value("${jwt.create.token.secret.key}")
    String secretKey;

    public String createJwtToken(UserDomain userDomain) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDomain.getUserName());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        byte[] apiKeySecretBytes = Base64.getDecoder().decode(secretKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());


        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*24))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token, String userName, Response response) {
        try {
            String userNameInToken = extractUsernameFromToken(token);

            if (isTokenExpired(token)) {
                addErrorCodeToResponse(response, 401, "TokenExpired", "Token has expired");
                return false;
            }

            if (!userNameInToken.equals(userName)) {
                addErrorCodeToResponse(response, 401, "InvalidToken", "Username doesn't match");
                return false;
            }
        } catch (Exception e) {
            log.info("Error Occurred while validating token:: ", e);
            addErrorCodeToResponse(response, 401, "InvalidToken", "Invalid Token");
            return false;
        }

        return true;
    }

    private boolean isTokenExpired(String token) {
        return extractDateFromJwtToken(token).before(new Date());
    }

    private String extractUsernameFromToken(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private Date extractDateFromJwtToken(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllCLaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllCLaims(String token) {
        JwtParser parser = Jwts.parserBuilder().setSigningKey(secretKey).build();
        return parser.parseClaimsJws(token).getBody();
    }

}
