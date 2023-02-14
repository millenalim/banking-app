package learn.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import learn.entity.AppUser;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtConverter {
    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final String ISSUER = "banking-app";
    private final int EXPIRATION_MINUTES = 60 * 24;
    private final int EXPIRATION_MILLIS = EXPIRATION_MINUTES * 60 * 1000;

    public String getTokenFromUser(AppUser user) {

        String authorities = user.getAuthorities().stream()
                .map(i -> i.getAuthority())
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setIssuer(ISSUER)
                .setSubject(user.getUsername())
                .claim("app_user_id", user.getUserId())
                .claim("authorities", authorities)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MILLIS))
                .signWith(key)
                .compact();
    }

    public AppUser getUserFromToken(String token) {

        if (token == null || !token.startsWith("Bearer ")) {
            return null;
        }

        try {
            Jws<Claims> jws = Jwts.parserBuilder()
                    .requireIssuer(ISSUER)
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token.substring(7));

            String email = jws.getBody().getSubject();
            int userId = (int)jws.getBody().get("app_user_id");
            String authStr = (String) jws.getBody().get("authorities");

            return new AppUser(userId, email, null, true,
                    Arrays.asList(authStr.split(",")));

        } catch (JwtException ex) {
            System.out.println(ex);
        }

        return null;
    }
}
