package br.com.store.service;

import br.com.store.dto.ProductDTO;
import br.com.store.entites.Product;
import br.com.store.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    public ProductRepository productRepository;

    public Product save(Product product){
      return productRepository.save(product);
    }

    public void delete(Long id){
        Product product = productRepository.findById(id).get();
        productRepository.delete(product);
    }

    public List<Product> findAllProducts(){
        return productRepository.findAll();
    }

    public Optional<Product> findByProductId(Long id){
        return productRepository.findById(id);
    }

    public void addStock(ProductDTO productDto){
        Product product = findByProductId(productDto.getId()).get();
        if(productDto.getQuantity() <= 0){
            throw new NumberFormatException("Numero inválido, insira quantidade maior que 0");
        }
        else{
            product.setQuantity(product.getQuantity() + productDto.getQuantity());
            save(product);
        }
    }
    public void attPrice(ProductDTO productDTO){
        Product product = findByProductId(productDTO.getId()).get();
        if(productDTO.getPrice() <= 0){
            throw new NumberFormatException("Preço inválido, insira um preço maior que 0");
        }
        else{
            product.setPrice(BigDecimal.valueOf(productDTO.getPrice()));
            save(product);
        }
    }
    public BigDecimal saleProduct(Long id, Integer quantity){
        Product product = findByProductId(id).get();
        BigDecimal price = null;
        if(quantity > product.getQuantity()){
            throw new NumberFormatException("Numero de peças maior que em estoque");
        }
        else{
            price = product.getPrice().multiply(BigDecimal.valueOf(quantity));
            product.setQuantity(product.getQuantity() - quantity);
            save(product);
            return price;
            //return sucessfull com mensagem de compra realizada com sucesso
        }
    }
    //Método para fazer pesquisa por nome de peça
    public List<Product> findFilter(Product filter){
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filter, matcher);
        return productRepository.findAll(example);
    }

    public boolean findByName(String name){
        if(productRepository.findByName(name).isPresent()){
            return true;
        }
        else{
            return false;
        }
    }
    //Fazer um método de desconto
}
