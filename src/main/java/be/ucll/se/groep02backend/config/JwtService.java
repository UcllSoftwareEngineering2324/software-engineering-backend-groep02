package be.ucll.se.groep02backend.config;

import java.security.Key;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "pFD3ofxirK/6rGeSMgLryPT5cZx2FPi8l3pPJxoA9sjsUiFSISVKUAsPd7/nO/Cte0eQ3pToF5PToYF3EaBORH+AG+CH4L06MMHC3dHusk+LWvMDj1lGoEGS/9fYCSIUfvxw3OLLl+ki5VP1jUwc5YwOACHSnilerC+XRJTRWJ3uB5gpRExHJalD2niuwYoBI4II0h0e22vJohZi/758G5pWxsQJ0OoEEWtV830T/zv5kwXVE6U0Y35auQWfZlbjv1rAWD5OoUEgIeMZYXXlqLeZm3l0AZsdgyu45kUc7Bo7nFxABIS5EhShRqvbzcIvWlnxWdvieBF9shy5FooeNQ==";

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
        extraClaims = new HashMap<>();
        extraClaims.put("role", userDetails.getAuthorities().toArray()[0].toString());
        // extraClaims.put("email", userDetails.getEmail());
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
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
