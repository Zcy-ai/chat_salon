package com.sr03.chat_salon.utils;

import com.sr03.chat_salon.model.User;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    // Clé secrète utilisée pour signer le token JWT
    private String SECRET_KEY = "zcy88827@gmail.com";
    // Durée d'expiration du token en millisecondes (ici, 7 jours)
    public long EXPIRITION = 1000 * 24 * 60 * 60 * 7;
    // Génère un token JWT à partir du nom d'utilisateur (login)
    public String generateToken(String login) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRITION);

        return Jwts.builder()
                .setSubject(login)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
    // Récupère le nom d'utilisateur (login) à partir du token JWT
    public String getUserLoginFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
    // Récupère la date d'expiration à partir du token JWT
    public Date getExpirationDateFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getExpiration();
    }
    // Valide le token JWT en vérifiant si le nom d'utilisateur correspond et si le token n'a pas expiré
    public Boolean validateToken(String token, UserDetails userDetails) {
        User user = (User) userDetails;
        String login = getUserLoginFromJWT(token);
        return (login.equals(user.getUsername()) && !isTokenExpired(token));
    }
    // Vérifie si le token JWT a expiré en comparant la date d'expiration avec la date actuelle
    public Boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
}