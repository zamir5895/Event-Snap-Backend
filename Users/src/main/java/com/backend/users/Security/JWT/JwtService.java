package com.backend.users.Security.JWT;

import com.auth0.jwt.algorithms.Algorithm;
import com.backend.users.Security.Exceptions.UnauthorizedException;

import java.util.Date;

import com.auth0.jwt.JWT;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${security.jwt.secret-key}")
    private String key;

    @Value("${security.jwt.expiration-time}")
    private Long expirationTIme;

    public String extractUsername(String token) {
        try{
            return JWT.decode(token).getSubject();
        }catch (Exception e){
            throw new UnauthorizedException("User is not authorized to access this resource");
        }
    }

    private String extractClaim(String token, String claim){
        return JWT.decode(token).getClaim(claim).asString();
    }

    public String generateToken(UserDetails details){
        Date now = new Date();
        Date expiryDate = new Date(System.currentTimeMillis() + expirationTIme);
        Algorithm algorithm = Algorithm.HMAC256(key);

        return JWT.create()
                .withSubject(details.getUsername())
                .withIssuedAt(now)
                .withExpiresAt(expiryDate)
                .sign(algorithm);
    }

    public Boolean validateToken(String token, String email){
        try{
            JWT.require(Algorithm.HMAC256(key)).build().verify(token);
            String username = extractUsername(token);
            return username.equals(email);
        }catch (Exception e){
            return Boolean.FALSE;
        }
    }

    public void invalidateToken(){
        SecurityContextHolder.clearContext();
    }

    public Date getExpirationDate(String token){
        try{
            return JWT.decode(token).getExpiresAt();
        }catch (Exception e){
            throw new UnauthorizedException("User is not authorized to access this resource");
        }
    }
}