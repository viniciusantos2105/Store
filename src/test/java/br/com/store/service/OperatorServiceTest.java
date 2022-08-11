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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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
    void whenDeleteOperatorThenReturnSuccessful() {
        when(operatorRepository.findById(anyLong())).thenReturn(operatorOptional);
        doNothing().when(operatorRepository).delete(any());
        operatorService.deleteOperator(operatorAdmin, operatorSalesman);
        verify(operatorRepository, times(1)).delete(any());
    }

    @Test
    void whenFindByUsernameThenReturnBoolean() {
        when(operatorRepository.findByUsername(anyString())).thenReturn(operatorOptional);

        boolean response = operatorService.findByUsername(USERNAME);

        assertNotNull(response);
        assertTrue(response);
    }

    @Test
    void whenChangeResponsibilityThenReturnSuccessful() {
        when(operatorRepository.save(any())).thenReturn(operatorAdmin);

        Operator response = operatorService.changeResponsibility(operatorAdmin, operatorSalesman, Responsibility.ADMIN.getResponsibility());

        assertNotNull(response);
        assertEquals(Operator.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(USERNAME, response.getUsername());
        assertEquals(PASSWORD, response.getPassword());
        assertEquals(Responsibility.ADMIN, response.getResponsibility());
    }

    @Test
    void whenFindByIdThenReturnAnInstanceOperator() {
        when(operatorRepository.findById(anyLong())).thenReturn(operatorOptional);

        Operator response = operatorService.findById(ID);

        assertNotNull(response);
        assertEquals(Operator.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(USERNAME, response.getUsername());
        assertEquals(PASSWORD, response.getPassword());
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