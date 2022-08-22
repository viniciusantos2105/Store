package br.com.store.controller;

import br.com.store.dto.CredentialsDTO;
import br.com.store.dto.OperatorDTO;
import br.com.store.dto.TokenDTO;
import br.com.store.entites.Client;
import br.com.store.entites.Operator;
import br.com.store.exceptions.CpfAlreadyExistsException;
import br.com.store.exceptions.EmailAlreadyExistisException;
import br.com.store.exceptions.PasswordInvalidException;
import br.com.store.exceptions.UsernameAlreadyExistsException;
import br.com.store.security.JwtService;
import br.com.store.service.ClientService;
import br.com.store.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin("*")
@Controller
@RestController
@RequestMapping("/operator")
public class OperatorController {

    @Autowired
    OperatorService operatorService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ClientService clientService;

    @Autowired
    JwtService jwtService;


    @PostMapping("/create")
    public Operator createOperator(@RequestBody Operator operator){
        if(operatorService.findByUsername(operator.getUsername()) == true || clientService.findByUsername(operator.getUsername())){
            throw new UsernameAlreadyExistsException();
        }
        else {
            operator.setPassword(passwordEncoder.encode(operator.getPassword()));
            return operatorService.createOperator(operator);
        }
    }

    @GetMapping("/getOperator")
    public Operator getOperator(@RequestBody OperatorDTO operatorDTO){
        return operatorService.findByUsernameGet(operatorDTO.getUsername());
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
    public Operator updateResponsibility(@RequestBody OperatorDTO operatorDTO, @RequestHeader("Authorization") String token){
        String divisor = token;
        String username = jwtService.getClientUsername(divisor.substring(7));
        Operator promotedOperator = operatorService.findById(operatorDTO.getId());
        Operator operator = operatorService.findByUsernameGet(username);
        return operatorService.changeResponsibility(operator, promotedOperator, operatorDTO.getResponsibility().getResponsibility());
    }

    @PostMapping("/login")
    public TokenDTO login(@RequestBody CredentialsDTO credentialsDTO){
        try{
            Operator operator = Operator.builder().username(credentialsDTO.getUsername())
                    .password(credentialsDTO.getPassword()).build();
            UserDetails userLogin = operatorService.authentic(operator);
            String token = jwtService.generatingTokenOperator(operator);
            return new TokenDTO(operator.getUsername(), token);
        }catch (UsernameNotFoundException | PasswordInvalidException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @GetMapping("/findOperators")
    public List<Operator> listOperators(@RequestHeader("Authorization") String token){
        String divisor = token;
        String username = jwtService.getClientUsername(divisor.substring(7));
        Operator operator = operatorService.findByUsernameGet(username);
        return operatorService.findAll(operator);
    }
}
