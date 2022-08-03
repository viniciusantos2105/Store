package br.com.store.service;

import br.com.store.dto.ClientDTO;
import br.com.store.dto.ProductDTO;
import br.com.store.entites.Address;
import br.com.store.entites.Client;
import br.com.store.entites.Product;
import br.com.store.entites.Request;
import br.com.store.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class RequestService {

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

    public List<Request> findAllRequests(){
        return requestRepository.findAll();
    }

    public Request saleRequest(Long id1, Long id2, Integer quantity, String address, String number){
        Address addressFinal = new Address();
        List<String> listAddress = clientService.splitAddress(clientService.address(clientService.findAddressByCep(address)));
        addressFinal.setCep(listAddress.get(0).substring(1));
        addressFinal.setRua(listAddress.get(1).substring(2));
        addressFinal.setBairro(listAddress.get(2).substring(2));
        addressFinal.setCidade(listAddress.get(3).substring(2));
        addressFinal.setEstado(listAddress.get(4).substring(2));
        addressFinal.setNumber(number);
        Client client = clientService.findByClientId(id1);
        Product product = productService.findByProductId(id2).get();
        BigDecimal price = productService.saleProduct(product.getId(), quantity);
        if(addressFinal == null){
            Request request = new Request(null, quantity, price, client, product, client.getAddress());
            saveRequest(request);
            return request;
        }
        else{
            Request request = new Request(null, quantity, price, client, product, addressFinal);
            saveRequest(request);
            return request;
        }
    }
}
