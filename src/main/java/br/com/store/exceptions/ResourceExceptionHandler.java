package br.com.store.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException ex, HttpServletRequest request){
        StandardError error = new StandardError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(CpfAlreadyExistsException.class)
    public ResponseEntity<StandardError> cpfAlreadyExistis(CpfAlreadyExistsException ex, HttpServletRequest request){
        StandardError error = new StandardError(LocalDateTime.now(), HttpStatus.CONFLICT.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<StandardError> usernameAlreadyExistis(UsernameAlreadyExistsException ex, HttpServletRequest request){
        StandardError error = new StandardError(LocalDateTime.now(), HttpStatus.CONFLICT.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<StandardError> productAlreadyExistis(ProductAlreadyExistsException ex, HttpServletRequest request){
        StandardError error = new StandardError(LocalDateTime.now(), HttpStatus.CONFLICT.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(AddressNotFoundExecption.class)
    public ResponseEntity<StandardError> addressNotFound(AddressNotFoundExecption ex, HttpServletRequest request){
        StandardError error = new StandardError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(EmailAlreadyExistisException.class)
    public ResponseEntity<StandardError> emailAlreadyExistis(EmailAlreadyExistisException ex, HttpServletRequest request){
        StandardError error = new StandardError(LocalDateTime.now(), HttpStatus.CONFLICT.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(ProductNotFoundExecption.class)
    public ResponseEntity<StandardError> productNotFound(ProductNotFoundExecption ex, HttpServletRequest request){
        StandardError error = new StandardError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DeniedAuthorization.class)
    public ResponseEntity<StandardError> deniedAuthorization(DeniedAuthorization ex, HttpServletRequest request){
        StandardError error = new StandardError(LocalDateTime.now(), HttpStatus.FORBIDDEN.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(ClientNotFoundExecption.class)
    public ResponseEntity<StandardError> clientNotFound(ClientNotFoundExecption ex, HttpServletRequest request){
        StandardError error = new StandardError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(RequestNotFoundExecption.class)
    public ResponseEntity<StandardError> requestNotFound(RequestNotFoundExecption ex, HttpServletRequest request){
        StandardError error = new StandardError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
