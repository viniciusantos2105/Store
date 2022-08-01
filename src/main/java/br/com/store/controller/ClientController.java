package br.com.store.controller;

import br.com.store.entites.Client;
import br.com.store.exceptions.CustomerAlreadyExistsException;
import br.com.store.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RestController
@RequestMapping("/register")
public class ClientController {

    @Autowired
    ClientService clientService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Client newClient(@RequestBody @Valid Client client){
        if(clientService.findByCpf(client.getCpf()) == true || clientService.findByUsername(client.getUsername()) == true){
            throw new CustomerAlreadyExistsException();
        }
        else{
            //String senhaCriptografada = passwordEncoder.encode(user.getPassword());
            //client.setPassword(senhaCriptografada);
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
}
