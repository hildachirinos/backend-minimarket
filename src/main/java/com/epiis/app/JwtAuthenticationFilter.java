package com.epiis.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.epiis.app.dataaccess.UsuarioRepository;
import com.epiis.app.entity.Usuario;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final JwtService jwtService;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public JwtAuthenticationFilter(JwtService jwtService) {
		this.jwtService = jwtService;
	}

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain) throws ServletException, IOException {
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		final String userName;

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);

			return;
		}

		jwt = authHeader.substring(7);
		userName = jwtService.extractUsername(jwt);

		if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			Usuario usuario = usuarioRepository.findByUserName(userName);

			if (usuario != null) {
				Map<String, String> userDetails = new HashMap<>();
				userDetails.put("userName", usuario.getUserName());
				userDetails.put("nombre", usuario.getNombre());
				userDetails.put("rol", usuario.getRol());

				if (jwtService.isTokenValid(jwt, userDetails)) {
					List<SimpleGrantedAuthority> authorities = new ArrayList<>();

					authorities.add(new SimpleGrantedAuthority("ROLE_" + usuario.getRol()));

					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
							null, authorities);

					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}
		}

		filterChain.doFilter(request, response);
	}
}
