package br.com.store.service;

import br.com.store.entites.*;
import br.com.store.enums.Responsibility;
import br.com.store.repository.RequestRepository;
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
import static org.mockito.Mockito.when;

@SpringBootTest
class RequestServiceTest {

    @Mock
    RequestRepository requestRepository;

    @InjectMocks
    RequestService requestService;

    private static final Long ID = 1L;
    private static final String USERNAME = "vinicius21";
    private static final String NAME = "Vinicius";
    private static final String CPF = "342.617.760-90";
    private static final String EMAIL = "vinicius@gmail.com";
    private static final String PASSWORD = "1234";
    private static final String CEP = "01530-070";
    private static final String RUA = "Rua Ágata";
    private static final String BAIRRO = "Aclimação";
    private static final String CIDADE = "São Paulo";
    private static final String ESTADO = "SP";
    private static final String NUMBER = "22";
    private static final BigDecimal PRICE = BigDecimal.valueOf(30);

    private Optional<Request> requestOptional;
    private Address address;
    private Request request;
    private Product product;
    private Client client;
    private Operator operator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        start();
    }

    @Test
    void whenSaveRequestThenReturnSuccessful() {
        when(requestRepository.save(any())).thenReturn(request);

        Request response = requestService.saveRequest(request);

        assertNotNull(response);
        assertEquals(Request.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(20, response.getQuantidade());
        assertEquals(PRICE, response.getPrice());
        assertEquals(client, response.getClient());
        assertEquals(product, response.getProduct());
        assertEquals(address, response.getAddress());
    }

    @Test
    void whenFindByRequestIdThenReturnSuccessful() {
        when(requestRepository.findById(anyLong())).thenReturn(requestOptional);

        Request response = requestService.findByRequestId(ID);

        assertNotNull(response);
        assertEquals(Request.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(20, response.getQuantidade());
        assertEquals(PRICE, response.getPrice());
        assertEquals(client, response.getClient());
        assertEquals(product, response.getProduct());
        assertEquals(address, response.getAddress());

    }

    @Test
    void whenFindAllRequestsThenReturnSuccessful() {
        when(requestRepository.findAll()).thenReturn(List.of(request));

        List<Request> response = requestService.findAllRequests(operator);

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(Request.class, response.get(0).getClass());
        assertEquals(ID, response.get(0).getId());
        assertEquals(20, response.get(0).getQuantidade());
        assertEquals(PRICE, response.get(0).getPrice());
        assertEquals(client, response.get(0).getClient());
        assertEquals(product, response.get(0).getProduct());
        assertEquals(address, response.get(0).getAddress());
    }

//    @Test
//    void whenSaleRequestThenReturnSuccessful() {
//        when(requestRepository.save(any())).thenReturn(request);
//
//        Request response = requestService.saleRequest(ID, ID, 20, " ", NUMBER);
//
//        assertNotNull(response);
//        assertEquals(Request.class, response.getClass());
//    }

    @Test
    void whenFindAllPurchasesThenReturnSuccessful() {
        when(requestRepository.findAll()).thenReturn(List.of(request));

        List<Request> response = requestService.findAllPurchases(client);

        assertNotNull(response);
    }

    private void start(){
        address = new Address(ID, CEP, RUA, BAIRRO, CIDADE, ESTADO, NUMBER);
//      client = new Client(ID, USERNAME, NAME, CPF, EMAIL, PASSWORD, address);
        product = new Product(ID, "Camisa", 20, PRICE);
  //      request = new Request(ID, 20, PRICE, client, product, address);
  //      requestOptional = Optional.of(new Request(ID, 20, PRICE, client, product, address));
        operator = new Operator(ID, USERNAME, PASSWORD, Responsibility.ADMIN);
    }
}