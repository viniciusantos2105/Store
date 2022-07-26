package br.com.store.controller;

import br.com.store.entites.Client;
import br.com.store.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/register")
public class ClientController {

    @Autowired
    ClientService clientService;

    @PostMapping("/createClient")
    public Client newClient(@RequestBody Client client){
      return clientService.save(client);
    }
}
