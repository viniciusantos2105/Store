package br.com.store.service;

import br.com.store.entites.Client;
import br.com.store.entites.Product;
import br.com.store.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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

    public List<Client> findFilter(Client filter){
        ExampleMatcher matcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filter, matcher);
        return clientRepository.findAll(example);
    }

}
