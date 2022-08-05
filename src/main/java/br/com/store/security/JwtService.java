package br.com.store.security;

import br.com.store.entites.Client;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
@Service
public class JwtService {

    @Value("${securirty.jwt.expiracao}")
    private String expiration;

    @Value("${security.jwt.chave-assinatura}")
    private String signatureKey;

    public String generatingToken(Client client){
        long expString = Long.valueOf(expiration);
        LocalDateTime dateHourExpiration = LocalDateTime.now().plusMinutes(expString);
        Instant instant = dateHourExpiration.atZone(ZoneId.systemDefault()).toInstant();
        Date data = Date.from(instant);
        return Jwts.builder()
                .setSubject(client.getUsername())//Colocar o login do usuario para saber quem ta fazendo a requisição
                .setExpiration(data).signWith(SignatureAlgorithm.HS512, signatureKey).compact();
    }

    private Claims gettingClaims(String token) throws ExpiredJwtException {
        return Jwts.parser().setSigningKey(signatureKey).parseClaimsJws(token).getBody();
    }

    public boolean tokenValid(String token){
        try{
            Claims claims = gettingClaims(token);
            Date dateExpiration = claims.getExpiration();
            LocalDateTime localDateTime = dateExpiration.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            return !LocalDateTime.now().isAfter(localDateTime);
        }catch (Exception e){
            return false;
        }
    }

    public String getClientUsername(String token) throws ExpiredJwtException{
        return gettingClaims(token).getSubject();
    }

}
