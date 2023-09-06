package com.app.securityexample.configuration;

import com.app.securityexample.model.UserPrinciple;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter
{

    private static final String BEARER = "Bearer ";

    private final JwtDecoder jwtDecoder;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        String bearerToken = getJwtFromRequest(request);
        if (Objects.nonNull(bearerToken)){
            Jwt jwt = jwtDecoder.decode(bearerToken);
            String username = jwt.getClaimAsString("preferred_username");
            String email = jwt.getClaimAsString("email");
            UserPrinciple userPrinciple = UserPrinciple.builder()
                .username(username)
                .email(email)
                .build();

            @SuppressWarnings("unchecked")
            Map<String, Collection<?>> realmAccess =
                (Map<String, Collection<?>>) jwt.getClaims().getOrDefault("realm_access", Collections.emptyList());
            Collection<?> roles = realmAccess.getOrDefault("roles", Collections.emptyList());
            List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(Object::toString)
                .map(SimpleGrantedAuthority::new)
                .toList();

            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userPrinciple, null, authorities));
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER)){
            return bearerToken.substring(7);
        }
        return null;
    }
}
