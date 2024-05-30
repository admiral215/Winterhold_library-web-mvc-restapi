package com.indocyber.winterhold.auth.api.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationFailureHandler failureHandler;

    public JwtRequestFilter(JwtService jwtService, UserDetailsService userDetailsService, AuthenticationFailureHandler failureHandler) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.failureHandler = failureHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try{
            //ambil dari http request header authorization
            var authorizationHeader = request.getHeader("Authorization");
            //apakah si header dalam bentuk token?
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
                //ambil tokennya
                var token = authorizationHeader.substring(7);

                //ambil claims dari token
                var claims = jwtService.getClaims(token);
                //ambil username, dari claims
                var username = claims.get("username", String.class);

                //apakah si token ini masih valid berdasarkan yang ada di database (User Details)
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtService.isValid(token, userDetails)) {
                    var authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }catch (Exception e){
            //jika error dia akan:
            failureHandler.onAuthenticationFailure(request,response, new BadCredentialsException("Token is Invalid"));
            return;
        }
        filterChain.doFilter(request,response);
    }
}
