package br.com.store.controller;

import br.com.store.entites.Product;
import br.com.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@Controller
public class ProductController {
    @Autowired
    public ProductService productService;

    @GetMapping("/allProducts")
    public List<Product> findAllProducts(){
       return productService.findAllProducts();
    }

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createProduct(@RequestBody Product product){
        productService.save(product);
      return ResponseEntity.ok().body(HttpStatus.CREATED);
    }

    @PostMapping("/addStock")
    public ResponseEntity addStock(@RequestBody Long id, Integer quantity){
        productService.addStock(id, quantity);
        return ResponseEntity.ok().body(HttpStatus.ACCEPTED);
    }
}
