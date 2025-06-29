package com.example.demo.application.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.example.demo.infrastructure.client.UserClient;

import DTOs.UserDto;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter{
	
	private final JWTService jwtService;
	
	private final UserClient userClient;
	
	@Autowired
	@Qualifier("handlerExceptionResolver")
	private HandlerExceptionResolver handlerExceptionResolver;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			final String requestTokenHeader = request.getHeader("Authorization");
			
			if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer")) {
                filterChain.doFilter(request, response);
                return;
            }
			
			String token = requestTokenHeader.split("Bearer")[1].trim();
			
			Long userId = jwtService.getUserIdFromToken(token);
			
			if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				ResponseEntity<UserDto> responseEntity = userClient.getUserById(userId);
				
				if (responseEntity.equals(ResponseEntity.noContent().build()))
					throw new RuntimeException("Token inválido");
				
				UserDto user = responseEntity.getBody();
				
				UsernamePasswordAuthenticationToken authenticationToken =
						new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
				authenticationToken.setDetails(
						new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				
			}
			
			filterChain.doFilter(request, response);
			
		}catch(JwtException e) {
			logger.error(e.getMessage());
            handlerExceptionResolver.resolveException(request,response,null,e);
		}
		
	}

}
