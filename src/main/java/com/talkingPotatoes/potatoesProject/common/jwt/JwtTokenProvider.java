package com.talkingPotatoes.potatoesProject.common.jwt;

import com.talkingPotatoes.potatoesProject.common.util.RedisUtil;
import com.talkingPotatoes.potatoesProject.user.dto.TokenDto;
import com.talkingPotatoes.potatoesProject.user.entity.Role;
import com.talkingPotatoes.potatoesProject.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    @Value("${jwt.token.secret}")
    private String secretKey;
    private Key key;
    @Value("${jwt.token.expire-length.access}")
    private String accessTokenValidMilSecond;
    @Value("${jwt.token.expire-length.refresh}")
    private String refreshTokenValidMilSecond;

    private final RedisUtil redisUtil;

    @PostConstruct
    protected void init() {
        this.key = Keys.hmacShaKeyFor(this.secretKey.getBytes());
    }

    public String createAccessToken(String id, String userId, Role role) {
        return generateToken(id, userId, role, Long.parseLong(accessTokenValidMilSecond));
    }

    public String createRefreshToken(String id, String userId, Role role) {
        redisUtil.setDataExpire(id, userId, Long.parseLong(refreshTokenValidMilSecond));
        return generateToken(id, userId, role, Long.parseLong(refreshTokenValidMilSecond));
    }

    public TokenDto createToken(String id, String userId, Role role) {
        return TokenDto.builder()
                .accessToken(createAccessToken(id, userId, role))
                .refreshToken(createRefreshToken(id, userId, role))
                .build();
    }

    protected String generateToken(String id, String userId, Role role, long tokenValidMilSecond) {
        Date now = new Date();
        return Jwts.builder()
                .claim("id", id)
                .claim("userId", userId)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMilSecond))
                .signWith(this.key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims resolveToken(HttpServletRequest req) {
        String token = req.getHeader("Authorization");
        if (token == null)
            return null;
        else if (token.contains("Bearer"))
            token = token.replace("Bearer ", "");
        else
            throw new DecodingException("");

        return getClaimsFromToken(token);
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Authentication getAuthentication(Claims claims) {
        return new UsernamePasswordAuthenticationToken(new User(claims), "", getAuthorities(claims));
    }

    public String getUserId(Claims claims) {
        return (String) claims.get("id");
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Claims claims) {
        return Arrays.stream(new String[]{claims.get("role").toString()})
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
