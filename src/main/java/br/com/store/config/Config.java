package br.com.store.config;

import br.com.store.entites.Product;
import br.com.store.repository.ProductRepository;
import org.hibernate.event.spi.InitializeCollectionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Arrays;

@Configuration
public class Config implements CommandLineRunner {
    @Autowired
    private ProductRepository repository;

    @Override
    public void run(String[] args){
        Product product = new Product(1L, "Camisa Puma", 35, BigDecimal.valueOf(39.90));
        Product product2 = new Product(2L, "Short Nike", 25, BigDecimal.valueOf(79.90));
        Product product3 = new Product(3L, "Saia Verde", 15, BigDecimal.valueOf(39.90));
        Product product4 = new Product(4L, "Bolsa Gucci", 75, BigDecimal.valueOf(350));
        Product product5 = new Product(5L, "Camisa Azul", 35, BigDecimal.valueOf(39.90));

        repository.saveAll(Arrays.asList(product, product2, product3, product4, product5));
    }
}
