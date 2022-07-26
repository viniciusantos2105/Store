package br.com.store.controller;

import br.com.store.dto.ProductDTO;
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

    @GetMapping("/findByProductId/{id}")
    public Product findByProdcutId(@PathVariable Long id){
        return productService.findByProductId(id);
    }

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createProduct(@RequestBody Product product){
        productService.save(product);
      return ResponseEntity.ok().body(HttpStatus.CREATED);
    }

    @PostMapping("/addStock")
    public ResponseEntity<HttpStatus> addStock(@RequestBody ProductDTO productDto){
        productService.addStock(productDto);
        return ResponseEntity.ok().body(HttpStatus.ACCEPTED);
    }

    @PostMapping("/attPrice")
    public ResponseEntity<HttpStatus> attPrice(@RequestBody ProductDTO productDTO){
        productService.attPrice(productDTO);
        return ResponseEntity.ok().body(HttpStatus.ACCEPTED);
    }
}
