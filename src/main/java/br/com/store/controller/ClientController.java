package br.com.store.controller;

import br.com.store.dto.ClientDTO;
import br.com.store.dto.CredentialsDTO;
import br.com.store.entites.Address;
import br.com.store.entites.Client;
import br.com.store.exceptions.CpfAlreadyExistsException;
import br.com.store.exceptions.EmailAlreadyExistisException;
import br.com.store.exceptions.UsernameAlreadyExistsException;
import br.com.store.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ClientService clientService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Client newClient(@RequestBody @Valid Client client){
        if(clientService.findByUsername(client.getUsername()) == true){
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
    public Client findById(@PathVariable Long id){
        return clientService.findByClientId(id);
    }

    @GetMapping("/findFilter")
    public List<Client> findFilter(@RequestBody Client filter){
        return clientService.findFilter(filter);
    }

    @PostMapping("/findcep")
    public ResponseEntity<Object> findAddresByCep(@RequestBody Address address) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.findAddressByCep(address.getCep()));
    }

    @PostMapping("/login")
    public UserDetails login(@RequestBody CredentialsDTO credentialsDTO){
//        try{
            Client client = Client.builder().username(credentialsDTO.getUsername())
                                            .password(credentialsDTO.getPassword()).build();
            UserDetails userLogin = clientService.authentic(client);
            return null;
//        //}
   }
}
