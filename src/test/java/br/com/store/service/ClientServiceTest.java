package br.com.store.service;

import br.com.store.entites.Address;
import br.com.store.entites.Client;
import br.com.store.repository.ClientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class ClientServiceTest {

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

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    private Client client;
    private Address address;
    private Optional<Client> clientOptional;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startClient();
    }

    @Test
    void save() {
    }

    @Test
    void whenFindByUsernameThenReturnAnClientInstance() {
    }

    @Test
    void findByUsernameGet() {
    }

    @Test
    void findByCpf() {
    }

    @Test
    void findByEmail() {
    }

    @Test
    void whenFindByClientIdThenReturnAnClientInstance() {
        when(clientRepository.findById(anyLong())).thenReturn(clientOptional);

        Client response = clientService.findByClientId(ID);

        assertNotNull(response);
        assertEquals(Client.class, response.getClass());
        assertEquals(USERNAME, response.getUsername());
        assertEquals(NAME, response.getName());
        assertEquals(CPF, response.getCpf());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());

        assertEquals(ID, response.getAddress().getId());
        assertEquals(CEP, response.getAddress().getCep());
        assertEquals(RUA, response.getAddress().getRua());
        assertEquals(BAIRRO, response.getAddress().getBairro());
        assertEquals(CIDADE, response.getAddress().getCidade());
        assertEquals(ESTADO, response.getAddress().getEstado());
        assertEquals(NUMBER, response.getAddress().getNumber());
    }

    @Test
    void findByClientIdSpecific() {
    }

    @Test
    void findFilter() {
    }

    @Test
    void findAddressByCep() {
    }

    @Test
    void address() {
    }

    @Test
    void splitAddress() {
    }

    @Test
    void loadUserByUsername() {
    }

    @Test
    void authentic() {
    }

    private void startClient(){
        client = new Client(ID, USERNAME, NAME, CPF, EMAIL, PASSWORD, address);
        address = new Address(ID, CEP, RUA, BAIRRO, CIDADE, ESTADO, NUMBER);
        clientOptional = Optional.of(new Client(ID, USERNAME, NAME, CPF, EMAIL, PASSWORD, address));
    }
}