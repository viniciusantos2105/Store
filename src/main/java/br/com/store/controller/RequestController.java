package br.com.store.controller;

import br.com.store.dto.RequestDTO;
import br.com.store.entites.Client;
import br.com.store.entites.Request;
import br.com.store.security.JwtService;
import br.com.store.service.ClientService;
import br.com.store.service.RequestService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.buf.UDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;


@RestController
@RequestMapping("/requests")
@Controller
public class RequestController {

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

    @GetMapping("/findAll")
    public List<Request> listAllSales(){
        return requestService.findAllRequests();
    }

    @GetMapping("/findClientPurchases")
    public List<Request> listAllPurchasesClient(@RequestHeader("Authorization") String token){
        String divisor = token;
        String username = jwtService.getClientUsername(divisor.substring(7));
        Client client = clientService.findByUsernameGet(username);
        return requestService.findAllPurchases(client);
    }

}
