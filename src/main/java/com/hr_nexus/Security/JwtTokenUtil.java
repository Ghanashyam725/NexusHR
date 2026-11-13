package com.hr_nexus.Security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.hr_nexus.ENUM.Permissions;
import com.hr_nexus.Entity.UserAuth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenUtil {

    private final Key key;
    private final long tokenValidate = 12 * 60 * 60 * 1000L;

    public JwtTokenUtil() {
        String secret = System.getenv("JWT_SECRET");

        if (secret == null || secret.isEmpty()) {
            secret = "Place this place with secert key";
        }

        key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(UserAuth user) {

        Map<String, Object> claim = new HashMap<>();
        claim.put("role", user.getRole().name());

        Set<Permissions> permission =
                RoleBasedPermissions.getRoleBasedPermision().get(user.getRole());

        Date now = new Date();
        Date expire = new Date(now.getTime() + tokenValidate);

        return Jwts.builder()
                .setClaims(claim)
                .setSubject(user.getUserEmail())
                .setIssuedAt(now)
                .setExpiration(expire)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Claims getClaim(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String gerUserEmail(String token) {
        return getClaim(token).getSubject();
    }

    public String extractToken(String header) {

        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }

        return null;
    }
}
