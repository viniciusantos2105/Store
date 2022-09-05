package br.com.store.controller;

import br.com.store.dto.ClientDTO;
import br.com.store.dto.CredentialsDTO;
import br.com.store.dto.TokenDTO;
import br.com.store.entites.Address;
import br.com.store.entites.Client;
import br.com.store.entites.Operator;
import br.com.store.exceptions.CpfAlreadyExistsException;
import br.com.store.exceptions.EmailAlreadyExistisException;
import br.com.store.exceptions.PasswordInvalidException;
import br.com.store.exceptions.UsernameAlreadyExistsException;
import br.com.store.repository.ClientRepository;
import br.com.store.security.JwtService;
import br.com.store.service.ClientService;
import br.com.store.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@Controller
@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    ClientService clientService;

    @Autowired
    ClientRepository repository;

    @Autowired
    OperatorService operatorService;

    @Autowired
    JwtService jwtService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Client newClient(@RequestBody @Valid Client client){
        if(clientService.findByUsername(client.getUsername()) == true || operatorService.findByUsername(client.getUsername())){
            throw new UsernameAlreadyExistsException();
        }
        if(clientService.findByCpf(client.getCpf()) == true){
            throw new CpfAlreadyExistsException();
        }
        if(clientService.findByEmail(client.getEmail()) == true){
            throw new EmailAlreadyExistisException();
        }
        else{
            client.setPassword(passwordEncoder.encode(client.getPassword()));
            return clientService.save(client);
        }
    }

    @GetMapping("/findById{id}")
    public Client findById(@PathVariable Long id, @RequestHeader("Authorization") String token){
        String divisor = token;
        String username = jwtService.getClientUsername(divisor.substring(7));
        Operator operator = operatorService.findByUsernameGet(username);
        return clientService.findByClientIdSpecific(id, operator);
    }

    @GetMapping("/findByIdClient{id}")
    public Client findByIdClient(@PathVariable Long id, @RequestHeader("Authorization") String token){
        String divisor = token;
        String username = jwtService.getClientUsername(divisor.substring(7));
        Client client = clientService.findByUsernameGet(username);
        return clientService.findByClientIdClient(id, client);
    }

    @GetMapping("/getClient")
    public Client getClient(@RequestHeader("Authorization") String token){
        String divisor = token;
        String username = jwtService.getClientUsername(divisor.substring(7));
        Client client = clientService.findByUsernameGet(username);
        return client;
    }

    @PostMapping("/getClientPost")
    public Client getClientPost(@RequestBody String username){
        return repository.findByUsername(username).get();
    }

    @GetMapping("/findFilter")
    public List<Client> findFilter(@RequestBody Client filter, @RequestHeader("Authorization") String token){
        String divisor = token;
        String username = jwtService.getClientUsername(divisor.substring(7));
        Operator operator = operatorService.findByUsernameGet(username);
        return clientService.findFilter(filter, operator);
    }

    @PostMapping("/findcep")
    public ResponseEntity<Object> findAddresByCep(@RequestBody Address address) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.findAddressByCep(address.getCep()));
    }

    @PostMapping("/login")
    public TokenDTO login(@RequestBody CredentialsDTO credentialsDTO){
        try{
            Client client = Client.builder().username(credentialsDTO.getUsername())
                                            .password(credentialsDTO.getPassword()).build();
            UserDetails userLogin = clientService.authentic(client);
            String token = jwtService.generatingToken(client);
            return new TokenDTO(client.getUsername(), token);
        }catch (UsernameNotFoundException | PasswordInvalidException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
   }

   @PatchMapping("/updateAddress")
    public Client updateAddress(@RequestHeader("Authorization") String token, @RequestBody Client newAddressClient){
       String divisor = token;
       String username = jwtService.getClientUsername(divisor.substring(7));
       Client oldAddressClient = clientService.findByUsernameGet(username);
       return clientService.updateAddresClient(newAddressClient, oldAddressClient);
   }

    @PatchMapping("/updateEmail")
    public Client updateEmail(@RequestHeader("Authorization") String token, @RequestBody Client newEmail){
        String divisor = token;
        String username = jwtService.getClientUsername(divisor.substring(7));
        Client oldEmail = clientService.findByUsernameGet(username);
        return clientService.updateEmailClient(newEmail, oldEmail);
    }

}
