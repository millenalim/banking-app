package learn.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import learn.entity.AppUser;
import org.springframework.stereotype.Component;

import java.security.Key;
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
                .claim("app_user_id", user.getAppUserId())
    }
}
