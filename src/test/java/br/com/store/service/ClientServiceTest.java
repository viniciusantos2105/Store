package br.com.store.service;

import br.com.store.entites.Address;
import br.com.store.entites.Client;
import br.com.store.entites.Operator;
import br.com.store.enums.Responsibility;
import br.com.store.repository.ClientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
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

    private static final Responsibility ADMIN = Responsibility.ADMIN;

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;
    private Client client;
    private Address address;
    private Operator operator;
    private Optional<Client> clientOptional;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startClient();
    }

    @Test
    void whenSaveThenReturnSucessfullCreate() {
        when(clientRepository.save(any())).thenReturn(client);

        Client response = clientService.save(client);

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
    void whenFindByUsernameThenReturnAnClientBoolean() {
        when(clientRepository.findByUsername(anyString())).thenReturn(clientOptional);

        boolean validate = clientService.findByUsername(USERNAME);

        assertTrue(validate);
    }

    @Test
    void whenFindByUsernameGetThenReturnAnClientInstance() {
        when(clientRepository.findByUsername(anyString())).thenReturn(clientOptional);

        Client response = clientService.findByUsernameGet(USERNAME);

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
    void whenFindByCpfThenReturnBoolean() {
        when(clientRepository.findByCpf(anyString())).thenReturn(clientOptional);

        boolean validate = clientService.findByCpf(CPF);

        assertTrue(validate);
    }

    @Test
    void whenFindByEmailThenReturnBoolean() {
        when(clientRepository.findByEmail(anyString())).thenReturn(clientOptional);

        boolean validate = clientService.findByEmail(EMAIL);

        assertTrue(validate);
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
    void whenFindByClientIdSpecificThenReturnAnClientInstance() {
        when(clientRepository.findById(anyLong())).thenReturn(clientOptional);

        Client response = clientService.findByClientIdSpecific(ID, operator);

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

        assertEquals(ID, operator.getId());
        assertEquals(USERNAME, operator.getUsername());
        assertEquals(PASSWORD, operator.getPassword());
        assertEquals(ADMIN, operator.getResponsibility());
    }

    @Test
    void whenFindFilterThenReturnClientsInstance() {
        when(clientRepository.findAll()).thenReturn(List.of(client));

        List<Client> response = clientService.findFilter(client, operator);

        assertNotNull(response);
    }

    @Test
    void whenFindAddressByCepThenReturn () {
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
        address = new Address(ID, CEP, RUA, BAIRRO, CIDADE, ESTADO, NUMBER);
        client = new Client(ID, USERNAME, NAME, CPF, EMAIL, PASSWORD, address);
        operator = new Operator(ID, USERNAME, PASSWORD, ADMIN);
        clientOptional = Optional.of(new Client(ID, USERNAME, NAME, CPF, EMAIL, PASSWORD, address));
    }
}