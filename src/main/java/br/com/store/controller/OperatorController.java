package br.com.store.controller;

import br.com.store.dto.CredentialsDTO;
import br.com.store.dto.TokenDTO;
import br.com.store.entites.Client;
import br.com.store.entites.Operator;
import br.com.store.exceptions.PasswordInvalidException;
import br.com.store.security.JwtService;
import br.com.store.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/operator")
public class OperatorController {

    @Autowired
    OperatorService operatorService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtService jwtService;


    @PostMapping("/create")
    public Operator createOperator(@RequestBody Operator operator){
       return operatorService.createOperator(operator);
    }

    @DeleteMapping("/delete")
    public void deleteOperator(@RequestBody Long id, @RequestHeader("Authorization") String token){
        String divisor = token;
        String username = jwtService.getClientUsername(divisor.substring(7));
        Operator firedOperator = operatorService.findById(id);
        Operator operator = operatorService.findByUsernameGet(username);
        operatorService.deleteOperator(operator, firedOperator);
    }

    @PatchMapping("/updateResponsibility")
    public Operator updateResponsibility(@RequestBody Long id, String responsibility, @RequestHeader("Authorization") String token){
        String divisor = token;
        String username = jwtService.getClientUsername(divisor.substring(7));
        Operator promotedOperator = operatorService.findById(id);
        Operator operator = operatorService.findByUsernameGet(username);
        return operatorService.changeResponsibility(operator, promotedOperator, responsibility);
    }

    @PostMapping("/login")
    public TokenDTO login(@RequestBody CredentialsDTO credentialsDTO){
        try{
            Operator operator = Operator.builder().name(credentialsDTO.getUsername())
                    .password(credentialsDTO.getPassword()).build();
            UserDetails userLogin = operatorService.authentic(operator);
            String token = jwtService.generatingTokenOperator(operator);
            return new TokenDTO(operator.getName(), token);
        }catch (UsernameNotFoundException | PasswordInvalidException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
