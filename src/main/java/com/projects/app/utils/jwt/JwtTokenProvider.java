//package com.projects.app.utils.jwt;
//
//import com.projects.app.models.user.User;
//import io.jsonwebtoken.*;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
//@Component
//@Slf4j
//public class JwtTokenProvider {
//    private final String JWT_SECRET = "somethingsreallyreallylongggggg";
//
//    private final long JWT_EXPIRATION = 604800000L;
//
//    /**
//     * Generate jwt token
//     *
//     * @param user user's info
//     * @return jwt token
//     */
//    public String generateToken(User user) {
//        Date now = new Date();
//        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
//        // Generate jwt token from id of user
//        return Jwts.builder()
//                .setSubject(Long.toString(user.getId()))
//                .setIssuedAt(now)
//                .setExpiration(expiryDate)
//                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
//                .compact();
//    }
//
//    /**
//     * Get user's info from jwt token
//     *
//     * @param token jwt token
//     * @return user's id
//     */
//    public Long getUserIdFromJWT(String token) {
//        Claims claims = Jwts.parser()
//                .setSigningKey(JWT_SECRET)
//                .parseClaimsJws(token)
//                .getBody();
//
//        return Long.parseLong(claims.getSubject());
//    }
//
//
//    public String getUsernameFromJWT(String token) {
//        Claims claims = Jwts.parser()
//                .setSigningKey(JWT_SECRET)
//                .parseClaimsJws(token)
//                .getBody();
//        return claims.getSubject();
//    }
//
//    /**
//     * Validate token
//     *
//     * @param authToken jwt token to be validated
//     * @return boolean
//     */
//    public boolean validateToken(String authToken) {
//        try {
//            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
//            return true;
//        } catch (MalformedJwtException ex) {
//            log.error("Invalid JWT token");
//        } catch (ExpiredJwtException ex) {
//            log.error("Expired JWT token");
//        } catch (UnsupportedJwtException ex) {
//            log.error("Unsupported JWT token");
//        } catch (IllegalArgumentException ex) {
//            log.error("JWT claims string is empty.");
//        }
//        return false;
//    }
//}
