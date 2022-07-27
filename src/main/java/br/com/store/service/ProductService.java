package br.com.store.service;

import br.com.store.dto.ProductDTO;
import br.com.store.entites.Product;
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

    public void delete(Long id){
        Product product = productRepository.findById(id).get();
        productRepository.delete(product);
    }

    public List<Product> findAllProducts(){
        return productRepository.findAll();
    }

    public Product findByProductId(Long id){
        return productRepository.findById(id).get();
    }

    public void addStock(ProductDTO productDto){
        Product product = findByProductId(productDto.getId());
        if(productDto.getQuantity() <= 0){
            throw new NumberFormatException("Numero inválido, insira quantidade maior que 0");
        }
        else{
            product.setQuantity(product.getQuantity() + productDto.getQuantity());
            save(product);
        }
    }

    public void attPrice(ProductDTO productDTO){
        Product product = findByProductId(productDTO.getId());
        if(productDTO.getPrice() <= 0){
            throw new NumberFormatException("Preço inválido, insira um preço maior que 0");
        }
        else{
            product.setPrice(BigDecimal.valueOf(productDTO.getPrice()));
            save(product);
        }
    }

    public BigDecimal saleProduct(Long id, Integer quantity){
        Product product = findByProductId(id);
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

    //Fazer um método de desconto
}
