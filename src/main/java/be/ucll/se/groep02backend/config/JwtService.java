package be.ucll.se.groep02backend.config;

import java.security.Key;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;

@Service
public class JwtService {
    
    @Value("${jwt.secretKey}")
    private String secretKey;


    public String extractUsername(String jWTtoken) {
        return extractClaim(jWTtoken, Claims::getSubject);
    }

    public <T> T extractClaim(String jWTtoken, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jWTtoken);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails) {
                
            List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        extraClaims.put("roles", roles.toArray(new String[0]));

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Boolean isTokenValid(String jWTtoken, UserDetails userDetails) {
        final String username = extractUsername(jWTtoken);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(jWTtoken));
    }

    private boolean isTokenExpired(String jWTtoken) {
        return extractExpiration(jWTtoken).before(new Date());
    }

    private Date extractExpiration(String jWTtoken) {
        return extractClaim(jWTtoken, Claims::getExpiration);

    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
