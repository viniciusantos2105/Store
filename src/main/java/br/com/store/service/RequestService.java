package br.com.store.service;

import br.com.store.dto.ClientDTO;
import br.com.store.dto.ProductDTO;
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

    public Request saleRequest(Long id1, Long id2, Integer quantity){
        Client client = clientService.findByClientId(id1);
        Product product = productService.findByProductId(id2).get();
        BigDecimal price = productService.saleProduct(product.getId(), quantity);
        Request request = new Request(null, quantity, price, client, product);
        saveRequest(request);
        return request;
    }
}
