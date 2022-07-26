package br.com.store.controller;

import br.com.store.dto.RequestDTO;
import br.com.store.entites.Request;
import br.com.store.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/requests")
@Controller
public class RequestController {

    @Autowired
    RequestService requestService;

    @PostMapping("/sale")
    public Request sale(@RequestBody RequestDTO requestDTO){
       return requestService.saleRequest(requestDTO.getId(), requestDTO.getId2(), requestDTO.getQuantity());
    }

    @GetMapping("/findAllSales")
    public List<Request> listAllSales(){
        return requestService.findAllRequests();
    }
}
