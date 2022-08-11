package br.com.store.service;

import br.com.store.dto.ProductDTO;
import br.com.store.entites.Operator;
import br.com.store.entites.Product;
import br.com.store.enums.Responsibility;
import br.com.store.exceptions.DeniedAuthorization;
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

    public Product saveProduct(Product product, Operator operator){
        if(operator.getResponsibility() != Responsibility.ADMIN && operator.getResponsibility() != Responsibility.STOCKHOLDER){
            throw new DeniedAuthorization();
        }
        return productRepository.save(product);
    }

    public void delete(Long id, Operator operator){
        if(operator.getResponsibility() != Responsibility.ADMIN && operator.getResponsibility() != Responsibility.STOCKHOLDER){
            throw new DeniedAuthorization();
        }
        Product product = productRepository.findById(id).get();
        productRepository.delete(product);
    }

    public List<Product> findAllProducts(){
        return productRepository.findAll();
    }

    public Optional<Product> findByProductId(Long id){
        return productRepository.findById(id);
    }

    public Product addStock(Long id, Integer quantity, Operator operator){
        if(operator.getResponsibility() != Responsibility.ADMIN && operator.getResponsibility() != Responsibility.STOCKHOLDER){
            throw new DeniedAuthorization();
        }
        else{
            Optional<Product> product = productRepository.findById(id);
            if(quantity <= 0){
                throw new NumberFormatException("Numero inválido, insira quantidade maior que 0");
            }
            else{
                Product product1 = product.get();
                product1.setQuantity(product1.getQuantity()+ quantity);
                return productRepository.save(product1);
            }
        }
    }
    public void attPrice(ProductDTO productDTO, Operator operator){
        if(operator.getResponsibility() != Responsibility.ADMIN && operator.getResponsibility() != Responsibility.STOCKHOLDER){
            throw new DeniedAuthorization();
        }
        else{
            Product product = findByProductId(productDTO.getId()).get();
            if(productDTO.getPrice() <= 0){
                throw new NumberFormatException("Preço inválido, insira um preço maior que 0");
            }
            else{
                product.setPrice(BigDecimal.valueOf(productDTO.getPrice()));
                save(product);
            }
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
