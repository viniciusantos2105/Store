package br.com.store.controller;

import br.com.store.dto.ProductDTO;
import br.com.store.entites.Operator;
import br.com.store.entites.Product;
import br.com.store.exceptions.ObjectNotFoundException;
import br.com.store.exceptions.ProductAlreadyExistsException;
import br.com.store.security.JwtService;
import br.com.store.service.OperatorService;
import br.com.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/product")
@Controller
public class ProductController {
    @Autowired
    public ProductService productService;

    @Autowired
    JwtService jwtService;

    @Autowired
    OperatorService operatorService;

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
    public Product createProduct(@RequestBody @Valid Product product, @RequestHeader("Authorization") String token){
        String divisor = token;
        String username = jwtService.getClientUsername(divisor.substring(7));
        Operator operator = operatorService.findByUsernameGet(username);
        if(productService.findByName(product.getName())){
            throw new ProductAlreadyExistsException();
        }
        else{
            return productService.saveProduct(product, operator);
        }
    }

    @DeleteMapping("/delete{id}")
    public void deleteProduct(@PathVariable Long id, @RequestHeader("Authorization") String token){
        String divisor = token;
        String username = jwtService.getClientUsername(divisor.substring(7));
        Operator operator = operatorService.findByUsernameGet(username);
        productService.delete(id, operator);
    }

    @PatchMapping("/addStock")
    public Product addStock(@RequestBody ProductDTO productDto, @RequestHeader("Authorization") String token){
        String divisor = token;
        String username = jwtService.getClientUsername(divisor.substring(7));
        Operator operator = operatorService.findByUsernameGet(username);
        return productService.addStock(productDto.getId(), productDto.getQuantity(), operator);
    }

    @PostMapping("/attPrice")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public HttpStatus attPrice(@RequestBody ProductDTO productDTO,  @RequestHeader("Authorization") String token){
        String divisor = token;
        String username = jwtService.getClientUsername(divisor.substring(7));
        Operator operator = operatorService.findByUsernameGet(username);
        productService.attPrice(productDTO, operator);
        return HttpStatus.ACCEPTED;
    }

    @PostMapping("/findFilter")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> findFilter(@RequestBody Product filter){
      return productService.findFilter(filter);
    }
}
