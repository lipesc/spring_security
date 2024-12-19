package lipe.com.springsecurity.config;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final String secretKey = System.getenv("JWT_SECRET");

  @Override
protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
        throws ServletException, IOException {
    String token = resolveToken(request);
    if (token != null && validateToken(token)) {
        logger.info("Token válido encontrado: " + token);
        UsernamePasswordAuthenticationToken auth = getAuthentication(token);
        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(auth);
    } else {
        logger.warn("Token inválido ou ausente");
    }
    filterChain.doFilter(request, response);
}


  private String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

  private boolean validateToken(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC512(secretKey);
      JWT.require(algorithm).build().verify(token);
      return true;
    } catch (JWTVerificationException e) {
      return false;
    }
  }

  private UsernamePasswordAuthenticationToken getAuthentication(String token) {
    DecodedJWT decodedJWT = JWT.decode(token);
    String username = decodedJWT.getSubject();
    List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("USER"));
    return new UsernamePasswordAuthenticationToken(username, null, authorities);
  }

}
