package br.com.store.security;

import br.com.store.service.ClientService;
import br.com.store.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthFilter extends OncePerRequestFilter {

    private JwtService jwtService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private OperatorService operatorService;

    public JwtAuthFilter(JwtService jwtService, ClientService clientService) {
        this.jwtService = jwtService;
        this.clientService = clientService;
    }

    public JwtAuthFilter(JwtService jwtService, OperatorService operatorService){
        this.jwtService = jwtService;
        this.operatorService = operatorService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        if(authorization != null && authorization.startsWith("Bearer")){
            String token = authorization.split(" ")[1];
            boolean isValid = jwtService.tokenValid(token);
            String usernamePresent = jwtService.getClientUsername(token);
            boolean isPresentClient = clientService.findByUsername(usernamePresent);
            boolean isPresentOperator = operatorService.findByUsername(usernamePresent);

            if(isValid){
                if(isPresentClient){
                String username = jwtService.getClientUsername(token);
                UserDetails usuario = clientService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(usuario, null,
                            usuario.getAuthorities());
                    user.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(user);
                }
                if(isPresentOperator){
                    String username = jwtService.getClientUsername(token);
                    UserDetails usuario = operatorService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(usuario, null,
                            usuario.getAuthorities());
                    user.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(user);
                }
                else{
                    throw new UsernameNotFoundException("Usuario não existe");
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
