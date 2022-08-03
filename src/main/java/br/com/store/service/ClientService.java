package br.com.store.service;

import br.com.store.entites.Client;
import br.com.store.exceptions.AddressNotFoundExecption;
import br.com.store.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

@Service
public class ClientService implements UserDetailsService {

    @Autowired
    ClientRepository clientRepository;

    public boolean findByUsername(String username){
        if(clientRepository.findByUsername(username).isPresent()){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean findByCpf(String cpf){
        if(clientRepository.findByCpf(cpf).isPresent()){
            return true;
        }
        else{
            return false;
        }
    }

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

    public String findAddressByCep(String cep) {
        String json;
        try {
            URL url = new URL("http://viacep.com.br/ws/"+ cep +"/json");
            URLConnection urlConnection = url.openConnection();
            InputStream is = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder jsonSb = new StringBuilder();
            br.lines().forEach(l -> jsonSb.append(l.trim()));
            json = jsonSb.toString();
            if(json.substring(2, 6).equals("erro")){
             throw new AddressNotFoundExecption();
            }
        } catch (Exception e) {
            throw new AddressNotFoundExecption();
        }
        return json;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
