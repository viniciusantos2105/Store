package br.com.store.controller;

import br.com.store.dto.ProductDTO;
import br.com.store.entites.Product;
import br.com.store.exceptions.ObjectNotFoundException;
import br.com.store.exceptions.ProductAlreadyExistsException;
import br.com.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/findByProductId{id}")
    public Product findByProductId(@PathVariable Long id){
        Optional<Product> product = productService.findByProductId(id);
      return product.orElseThrow(()-> new ObjectNotFoundException());
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody @Valid Product product){
        if(productService.findByName(product.getName()) == true){
            throw new ProductAlreadyExistsException();
        }
        else{
            //String senhaCriptografada = passwordEncoder.encode(user.getPassword());
            //client.setPassword(senhaCriptografada);
            return productService.save(product);
        }
    }

    @DeleteMapping("/delete{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public HttpStatus deleteProduct(@PathVariable Long id){
        productService.delete(id);
        return HttpStatus.ACCEPTED;
    }

    @PostMapping("/addStock")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public HttpStatus addStock(@RequestBody ProductDTO productDto){
        productService.addStock(productDto);
        return HttpStatus.ACCEPTED;
    }

    @PostMapping("/attPrice")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public HttpStatus attPrice(@RequestBody ProductDTO productDTO){
        productService.attPrice(productDTO);
        return HttpStatus.ACCEPTED;
    }

    @GetMapping("/findFilter")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> findFilter(@RequestBody Product filter){
      return productService.findFilter(filter);
    }
}
