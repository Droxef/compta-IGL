package ch.igl.compta.api.security;

import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.java.Log;

@Component
@Log
public class JwtUtil {
    // could use application.properties too
    // @Value("${jwt.secret}")
    private final String SECRET_KEY = System.getProperty("my.api.key");
    private final long EXPIRATION_MS = 1000 * 60 * 60 * 4; // 4 hours
    private final long REFRESH_EXPIRATION_MS = 1000 * 60 * 60 * 24 * 4; // 4 jours
    private SecretKey secretKey;

    public JwtUtil() {
        if(!StringUtils.hasText(SECRET_KEY)) {
            throw new RuntimeException("SECRET_KEY not set");
        }
    }

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new java.util.Date())
                .setExpiration(new java.util.Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(secretKey, io.jsonwebtoken.SignatureAlgorithm.HS512)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token, String username) {
        //String tokenUsername = extractUsername(token);
        //return (tokenUsername.equals(username) && !isTokenExpired(token));
        try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey).build()
                .parseClaimsJws(token);
                return true;
        } catch (SecurityException e) {
            log.log(Level.SEVERE, "Invalid JWT signature: {0}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.log(Level.SEVERE, "Invalid JWT token: {0}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.log(Level.SEVERE, "JWT token is expired: {0}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.log(Level.SEVERE, "JWT token is unsupported: {0}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.log(Level.SEVERE, "JWT claims string is empty: {0}", e.getMessage());
        }
        return false;
    }

    public boolean isTokenExpired(String token) {
        java.util.Date expiration = Jwts.parserBuilder()
                .setSigningKey(secretKey).build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new java.util.Date());
    }
}
