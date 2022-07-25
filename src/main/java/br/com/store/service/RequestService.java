package br.com.store.service;

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
    ProductService productService;
    ClientService clientService;

    public Request saveRequest(Request request){
        return requestRepository.save(request);
    }

    public Request findByRequestId(Long id){
        Request request = requestRepository.findById(id).get();
        return request;
    }

    public List<Request> findAllRequests(){
        List<Request> requestList = requestRepository.findAll();
        return requestList;
    }

    public Request createRequest(Long clientId, Long productId, Integer quantity){
        Client client = clientService.findByClientId(clientId);
        Product product = productService.findByProductId(productId);
        BigDecimal price = productService.saleProduct(product.getId(), quantity);
        Request request = new Request(null, quantity, price, client, product);
        saveRequest(request);
        return request;
    }
}
