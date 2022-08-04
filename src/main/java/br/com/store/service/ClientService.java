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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ClientService implements UserDetailsService {

    @Autowired
    ClientRepository clientRepository;

    public Client save(Client client){
        List<String> listAddress = splitAddress(address(findAddressByCep(client.getAddress().getCep())));
        client.getAddress().setCep(listAddress.get(0).substring(1));
        client.getAddress().setRua(listAddress.get(1).substring(2));
        client.getAddress().setBairro(listAddress.get(2).substring(2));
        client.getAddress().setCidade(listAddress.get(3).substring(2));
        client.getAddress().setEstado(listAddress.get(4).substring(2));
        return clientRepository.save(client);
    }

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

    public boolean findByEmail(String email){
        if(clientRepository.findByEmail(email).isPresent()){
            return true;
        }
        else{
            return false;
        }
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
            URL url = new URL("http://viacep.com.br/ws/" + cep + "/json");
            URLConnection urlConnection = url.openConnection();
            InputStream is = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder jsonSb = new StringBuilder();
            br.lines().forEach(l -> jsonSb.append(l.trim()));
            json = jsonSb.toString();
            if (json.substring(2, 6).equals("erro")) {
                throw new AddressNotFoundExecption();
            }
        } catch (Exception e) {
            throw new AddressNotFoundExecption();
        }
        return json;
    }

    public List<String> address(String json){
        String[] result = json.split("[\\W][,]" );
        List<String> list = new ArrayList<>();
        list.addAll(Arrays.asList(result[0], result[1], result[3], result[4], result[5]));
        return list;
    }

    public List<String> splitAddress(List<String> list){
        String cep = list.get(0);
        String rua = list.get(1);
        String bairro = list.get(2);
        String cidade = list.get(3);
        String estado = list.get(4);
        String[] resultCep = cep.split(": ");
        String[] resultRua = rua.split(":");
        String[] resultBairro = bairro.split(":");
        String[] resultCidade = cidade.split(":");
        String[] resultEstado = estado.split(":");
        List<String> listAddress = new ArrayList<>();
        listAddress.addAll(Arrays.asList(resultCep[1], resultRua[1], resultBairro[1], resultCidade[1], resultEstado[1]));
        return listAddress;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = clientRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Username inválido"));

    }
}
