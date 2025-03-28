package com.backend.users.Security.JWT;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.backend.users.Security.Exceptions.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;
    @Value("${security.jwt.expiration-time}")
    private Long expirationTime;

    public String extractUsername(String token){
        try{
            return JWT.decode(token).getSubject();
        }catch (JWTDecodeException e){
            throw new UnauthorizedException("Not authorized");

        }
    }
    public String extractClaim(String token, String claim) {

        return JWT.decode(token).getClaim(claim).asString();
    }
    public String generateToken(UserDetails data) {
        Date now = new Date();
        Date expiration = new Date(System.currentTimeMillis()+expirationTime);

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        return JWT.create()
                .withSubject(data.getUsername())
                .withIssuedAt(now)
                .withExpiresAt(expiration)
                .sign(algorithm);
    }

    public boolean validateToken(String token, String userEmail) {
        try {
            JWT.require(Algorithm.HMAC256(secretKey)).build().verify(token);

            String username = extractUsername(token);

            return username.equals(userEmail);
        } catch (Exception e) {
            return false;
        }
    }



    public void invalidateToken() {
        SecurityContextHolder.clearContext();
    }
    public Date getExpirationTime(String token) {
        try {
            return JWT.decode(token).getExpiresAt();
        } catch (JWTDecodeException e) {
            throw new UnauthorizedException("Invalid or malformed token");
        }
    }
}