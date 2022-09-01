package br.com.store.service;

import br.com.store.dto.ClientDTO;
import br.com.store.dto.ProductDTO;
import br.com.store.entites.*;
import br.com.store.enums.Responsibility;
import br.com.store.exceptions.DeniedAuthorization;
import br.com.store.exceptions.ProductNotFoundExecption;
import br.com.store.exceptions.RequestNotFoundExecption;
import br.com.store.repository.RequestRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RequestService {

    @Autowired
    OperatorService operatorService;
    @Autowired
    RequestRepository requestRepository;
    @Autowired
    ProductService productService;
    @Autowired
    ClientService clientService;

    public Request saveRequest(Request request){
        return requestRepository.save(request);
    }

    public Request findByRequestId(Long id){
      return requestRepository.findById(id).get();
    }

    public List<Request> findAllRequests(Operator operator){
        if(operator.getResponsibility() != Responsibility.ADMIN && operator.getResponsibility() != Responsibility.SALESMAN){
            throw new DeniedAuthorization();
        }
        else {
            return requestRepository.findAll();
        }
    }

    public Request saleRequest(Long id1, Long id2, Integer quantity, String address, String number){
        Address addressFinal = new Address();
        if(address != null){
            List<String> listAddress = clientService.splitAddress(clientService.address(clientService.findAddressByCep(address)));
            addressFinal.setCep(listAddress.get(0).substring(1));
            addressFinal.setRua(listAddress.get(1).substring(2));
            addressFinal.setBairro(listAddress.get(2).substring(2));
            addressFinal.setCidade(listAddress.get(3).substring(2));
            addressFinal.setEstado(listAddress.get(4).substring(2));
            addressFinal.setNumber(number);
            Client client = clientService.findByClientId(id1);
            Optional<Product> product = productService.findByProductId(id2);
            product.orElseThrow(()-> new ProductNotFoundExecption());
            BigDecimal price = productService.saleProduct(product.get().getId(), quantity);
            LocalDateTime timeLocal = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String time = timeLocal.format(formatter);
            Request request = new Request(null, quantity, price, time, client, product.get(), addressFinal);
            client.getPurchaseRecord().add(request);
            saveRequest(request);
            clientService.save(client);
            return request;
        }
        else{
            Client client = clientService.findByClientId(id1);
            Optional<Product> product = productService.findByProductId(id2);
            product.orElseThrow(()-> new ProductNotFoundExecption());
            BigDecimal price = productService.saleProduct(product.get().getId(), quantity);
            LocalDateTime timeLocal = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String time = timeLocal.format(formatter);
            Request request = new Request(null, quantity, price, time,  client, product.get(), client.getAddress());
            saveRequest(request);
            client.getPurchaseRecord().add(request);
            clientService.save(client);
            return request;
        }
    }

    public List<Request> findAllPurchases(Client client){
        return client.getPurchaseRecord();
    }

    public Request findBySpecificRequest(Long id, Client client){
        Request request = requestRepository.findById(id).orElseThrow(()-> new RequestNotFoundExecption());
        if(request.getClient().equals(client)){
            return request;
        }
        else{
            throw new DeniedAuthorization();
        }
    }
}
