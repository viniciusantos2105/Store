package br.com.store.service;

import br.com.store.dto.ProductDTO;
import br.com.store.entites.Client;
import br.com.store.entites.Operator;
import br.com.store.entites.Product;
import br.com.store.enums.Responsibility;
import br.com.store.repository.ClientRepository;
import br.com.store.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private static final Long ID = 1L;
    private static final String NAME = "Camisa";
    private static final Integer QUANTITY = 20;
    private static final BigDecimal PRICE = BigDecimal.valueOf(30);
    private static final BigDecimal PRICE2 = BigDecimal.valueOf(20);
    private static final String USERNAME = "vinicius21";
    private static final String PASSWORD = "1234";
    private static final Responsibility ADMIN = Responsibility.ADMIN;

    private Product product;
    private Operator operator;
    private Optional<Product> productOptional;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startProduct();
    }

    @Test
    void whenSaveThenReturnSuccessful() {
        when(productRepository.save(any())).thenReturn(product);

        Product response = productService.save(product);

        assertNotNull(response);
        assertEquals(Product.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(QUANTITY, response.getQuantity());
        assertEquals(PRICE, response.getPrice());
    }

    @Test
    void whenSaveProductThenReturnSuccessful() {
        when(productRepository.save(any())).thenReturn(product);

        Product response = productService.saveProduct(product, operator);

        assertNotNull(response);
        assertEquals(Product.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(QUANTITY, response.getQuantity());
        assertEquals(PRICE, response.getPrice());

        assertEquals(Responsibility.ADMIN, operator.getResponsibility());
    }

    @Test
    void whenDeleteThenReturnSuccessful() {
         when(productRepository.findById(anyLong())).thenReturn(productOptional);
         doNothing().when(productRepository).delete(any());
         productService.delete(productOptional.get().getId(), operator);
         verify(productRepository, times(1)).delete(any());
    }

    @Test
    void whenFindAllProductsThenReturnListAll() {
        when(productRepository.findAll()).thenReturn(List.of(product));

        List<Product> response = productService.findAllProducts();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(Product.class, response.get(0).getClass());
    }

    @Test
    void findByProductId() {
        when(productRepository.findById(anyLong())).thenReturn(productOptional);

        Optional<Product> response = productService.findByProductId(ID);

        assertNotNull(response);
        assertEquals(Product.class, response.get().getClass());
        assertEquals(ID, response.get().getId());
        assertEquals(NAME, response.get().getName());
        assertEquals(QUANTITY, response.get().getQuantity());
        assertEquals(PRICE, response.get().getPrice());
    }

//    @Test
//    void whenAddStockThenReturnSuccessful() {
//        when(productRepository.save(any())).thenReturn(product);
//
//        Product response = productService.addStock(product.getId(), QUANTITY, operator);
//
//        assertNotNull(response);
//        assertEquals(Product.class, response.getClass());
//        assertEquals(ID, response.getId());
//
//        assertEquals(QUANTITY, response.getQuantity());
//        assertEquals(PRICE, response.getPrice());
//    }

//    @Test
//    void attPrice() {
//        when(productRepository.save(any())).thenReturn(product);
//
//        productService.attPrice(productDTO, operator);
//
//        assertNotEquals(0, productDTO.getPrice());
//    }

//    @Test
//    void whenSaleProductThenReturnSuccessful() {
//        when(productRepository.save(any())).thenReturn(PRICE);
//
//        BigDecimal response = productService.saleProduct(ID, QUANTITY);
//
//        assertNotNull(response);
//        assertEquals(BigDecimal.class, response.getClass());
//    }

    @Test
    void whenFindFilterThenReturnProductsInstance() {
        when(productRepository.findAll()).thenReturn(List.of(product));

        List<Product> response = productService.findFilter(product);

        assertNotNull(response);
    }

    @Test
    void whenFindByNameThenReturnBoolean() {
        when(productRepository.findByName(anyString())).thenReturn(productOptional);

        boolean validate = productService.findByName(NAME);

        assertTrue(validate);
    }

    private void startProduct(){
        product = new Product(ID, NAME, QUANTITY, PRICE);
        operator = new Operator(ID, USERNAME, PASSWORD, ADMIN);
        productOptional = Optional.of(new Product(ID, NAME, QUANTITY, PRICE));
        productDTO = new ProductDTO(ID, QUANTITY, 30.00);
    }
}