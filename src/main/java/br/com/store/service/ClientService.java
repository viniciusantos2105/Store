package br.com.store.service;

import br.com.store.entites.Client;
import br.com.store.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    public Client save(Client client){
        return clientRepository.save(client);
    }

    public Client findByClientId(Long id){
        Client client = clientRepository.findById(id).get();
        return client;
    }

}
