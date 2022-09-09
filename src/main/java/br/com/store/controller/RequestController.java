package br.com.store.controller;

import br.com.store.dto.RequestDTO;
import br.com.store.entites.Client;
import br.com.store.entites.Operator;
import br.com.store.entites.Product;
import br.com.store.entites.Request;
import br.com.store.exceptions.ObjectNotFoundException;
import br.com.store.security.JwtService;
import br.com.store.service.ClientService;
import br.com.store.service.OperatorService;
import br.com.store.service.RequestService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.buf.UDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;

@CrossOrigin("*")
@RestController
@RequestMapping("/requests")
@Controller
public class RequestController {

    @Autowired
    OperatorService operatorService;

    @Autowired
    RequestService requestService;
    @Autowired
    JwtService jwtService;

    @Autowired
    ClientService clientService;

    @PostMapping("/sale")
    public Request sale(@RequestBody RequestDTO requestDTO, @RequestHeader("Authorization") String token){
        String divisor = token;
        String username = jwtService.getClientUsername(divisor.substring(7));
        Client client = clientService.findByUsernameGet(username);
        return requestService.saleRequest(client.getId(), requestDTO.getId(), requestDTO.getQuantity(),
               requestDTO.getAddress(), requestDTO.getNumber());
    }

    @GetMapping("/findByProductId{id}")
    public Request findByProductId(@PathVariable Long id, @RequestHeader("Authorization") String token){
       String divisor = token;
       String username = jwtService.getClientUsername(divisor.substring(7));
       Client client = clientService.findByUsernameGet(username);
       Request request = requestService.findByRequestId(id);
       return request;
    }

    @GetMapping("/findAll")
    public List<Request> listAllSales(@RequestHeader("Authorization") String token){
        String divisor = token;
        String username = jwtService.getClientUsername(divisor.substring(7));
        Operator operator = operatorService.findByUsernameGet(username);
        return requestService.findAllRequests(operator);
    }

    @GetMapping("/findClientPurchases")
    public List<Request> listAllPurchasesClient(@RequestHeader("Authorization") String token){
        String divisor = token;
        String username = jwtService.getClientUsername(divisor.substring(7));
        Client client = clientService.findByUsernameGet(username);
        requestService.findAllPurchases(client);
        return client.getPurchaseRecord();
    }
    @GetMapping("/findByRequestId{id}")
    public Request listAllSpecificRequest(@RequestHeader("Authorization") String token, @PathVariable Long id){
        String divisor = token;
        String username = jwtService.getClientUsername(divisor.substring(7));
        Operator operator = operatorService.findByUsernameGet(username);
        return requestService.findSpeficicRequest(id, operator);
    }

}
