package br.com.store.service;

import br.com.store.entites.Product;
import br.com.store.entites.Request;
import br.com.store.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    public ProductRepository productRepository;

    public void save(Product product){
        productRepository.save(product);
    }

    public List<Product> findAllProducts(){
        return productRepository.findAll();
    }

    public Product findByProductId(Long id){
        return productRepository.findById(id).get();
    }

    public Product addStock(Long id, Integer quantity){
        Product product = findByProductId(id);
        if(quantity <= 0){
            //return error e pedir para inserir quantiade valida
        }
        else{
            product.setQuantity(product.getQuantity() + quantity);
            return product; //return quantidade atualizada
        }
        return product;
    }
    public BigDecimal saleProduct(Long id, Integer quantity){
        Product product = findByProductId(id);
        BigDecimal price = null;
        if(quantity > product.getQuantity()){
            //Return erro com mensagem de quantitade maior que estoque
        }
        else{
            price = product.getPrice().multiply(BigDecimal.valueOf(quantity));
            product.setQuantity(product.getQuantity() - quantity);
            save(product);
            return price;
            //return sucessfull com mensagem de compra realizada com sucesso
        }
        return price;
    }

    //Fazer um método de desconto
}
