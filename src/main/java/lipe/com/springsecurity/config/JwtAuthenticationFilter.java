package lipe.com.springsecurity.config;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Arrays;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final String secretKey = System.getenv("JWT_SECRET");

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {
    String jwt = getJwtFromRequest(request);
    if (StringUtils.hasText(jwt) && validateToken(jwt)) {
      DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(secretKey)).build().verify(jwt);

      String username = decodedJWT.getSubject();
      String[] roles = decodedJWT.getClaim("roles").asArray(String.class);

      if (roles != null) {
        List<SimpleGrantedAuthority> authorities = Arrays.stream(roles)
            .map(SimpleGrantedAuthority::new)
            .toList();

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            username, null, authorities);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
      } else {
        // Log a warning if roles are null
        logger.warn("JWT token does not contain roles");
      }
    }
    filterChain.doFilter(request, response);
  }

  private String getJwtFromRequest(@NonNull HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

  private boolean validateToken(String token) {
    try {
      JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token);
      return true;
    } catch (Exception ex) {
      return false;
    }
  }

}
