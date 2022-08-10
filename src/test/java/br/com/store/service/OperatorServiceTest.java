package br.com.store.service;

import br.com.store.entites.Client;
import br.com.store.entites.Operator;
import br.com.store.enums.Responsibility;
import br.com.store.repository.ClientRepository;
import br.com.store.repository.OperatorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class OperatorServiceTest {

    private static final Long ID = 1L;
    private static final String USERNAME = "vinicius21";
    private static final String PASSWORD = "1234";

    @InjectMocks
    private OperatorService operatorService;

    @Mock
    private OperatorRepository operatorRepository;

    private Optional<Operator> operatorOptional;
    private Operator operatorAdmin;
    private Operator operatorSalesman;
    private Operator operatorStockholder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startOperator();
    }

    @Test
    void whenFindByUsernameGetReturnAnInstanceOperator() {
        when(operatorRepository.findByUsername(anyString())).thenReturn(operatorOptional);

        Operator response = operatorService.findByUsernameGet(USERNAME);

        assertEquals(ID, response.getId());
        assertEquals(USERNAME, response.getUsername());
        assertEquals(PASSWORD, response.getPassword());
        assertEquals(Responsibility.ADMIN, response.getResponsibility());
    }

    @Test
    void whenCreateOperatorReturnAnInstanceCreated() {
        when(operatorRepository.save(any())).thenReturn(operatorAdmin);

        Operator response = operatorService.createOperator(operatorAdmin);

        assertEquals(ID, response.getId());
        assertEquals(USERNAME, response.getUsername());
        assertEquals(PASSWORD, response.getPassword());
        assertEquals(Responsibility.ADMIN, response.getResponsibility());
    }

    @Test
    void deleteOperator() {
    }

    @Test
    void findByUsername() {
    }

    @Test
    void changeResponsibility() {
    }

    @Test
    void findById() {
    }

    @Test
    void loadUserByUsername() {
    }

    @Test
    void authentic() {
    }

    @Test
    void findAll() {
        when(operatorRepository.findAll()).thenReturn(List.of(operatorAdmin));

        List<Operator> response = operatorService.findAll(operatorAdmin);

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(Operator.class, response.get(0).getClass());
    }

    private void startOperator(){
        operatorAdmin = new Operator(ID, USERNAME, PASSWORD, Responsibility.ADMIN);
        operatorSalesman = new Operator(ID, USERNAME, PASSWORD, Responsibility.SALESMAN);
        operatorStockholder = new Operator(ID, USERNAME, PASSWORD, Responsibility.STOCKHOLDER);
        operatorOptional = Optional.of(new Operator(ID, USERNAME, PASSWORD, Responsibility.ADMIN));
    }
}