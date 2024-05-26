package team_pwp.swap_be.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.io.Decoders;
import team_pwp.swap_be.auth.dto.TokenDto;

@Component
public class JwtProvider {

    private final Key key;
    private static final String GRANT_TYPE = "Bearer";
    private static final long ACCESS_TOKEN_EXPIRES_IN = 1000L * 60 * 60 * 5; //access 5시간

    public JwtProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenDto generateTokenDto(Long userId) {

        long now = (new Date()).getTime();

        String accessToken = generateAccessToken(now, userId);

        return TokenDto.builder()
            .grantType(GRANT_TYPE)
            .accessToken(accessToken)
            .accessTokenExpiresIn(new Date(now + ACCESS_TOKEN_EXPIRES_IN).getTime())
            .build();
    }

    public String generateAccessToken(long now, Long userId) {
        String accessToken = Jwts.builder()
            .setSubject(String.valueOf(userId))
            .setExpiration(new Date(now + ACCESS_TOKEN_EXPIRES_IN))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();

        return accessToken;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | ExpiredJwtException |
                 UnsupportedJwtException |
                 IllegalArgumentException e) {
            throw e;
        }
    }

    public String parseAccessToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring("Bearer ".length());
            return token;
        } else {
            /**
             * AccessToken이 없거나 Bearer type이 아닐 경우
             * 에러 response 전달
             */
            throw new IllegalArgumentException("토큰이 없거나 Bearer type이 아닙니다.");
        }
    }

    public long getUserIdFromToken(String accessToken) {
        String userId = parseClaims(accessToken).getSubject();
        return Long.parseLong(userId);
    }

    protected Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
        } catch (ExpiredJwtException e) {
            throw e;
        }
    }


    public String generateAccessTokenForTest() {
        return Jwts.builder()
            .setSubject("1")
            .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRES_IN))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();
    }
}
