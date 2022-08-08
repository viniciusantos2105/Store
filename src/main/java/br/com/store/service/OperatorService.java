package br.com.store.service;

import br.com.store.entites.Client;
import br.com.store.entites.Operator;
import br.com.store.enums.Responsibility;
import br.com.store.exceptions.PasswordInvalidException;
import br.com.store.exceptions.UsernameAlreadyExistsException;
import br.com.store.repository.OperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class OperatorService implements UserDetailsService {

    @Autowired
    @Lazy
    PasswordEncoder encoder;

    @Autowired
    OperatorRepository operatorRepository;

    public Operator findByUsernameGet(String username){
        return operatorRepository.findByUsername(username).get();
    }

    public Operator createOperator(Operator operator){
        return operatorRepository.save(operator);
    }

    public void deleteOperator(Operator operator, Operator firedOperator){
        if(operator.getResponsibility() != Responsibility.ADMIN){
            throw new UsernameAlreadyExistsException();
        }
        else {
            operatorRepository.delete(firedOperator);
        }
    }

    public boolean findByUsername(String username){
        return operatorRepository.findByUsername(username).isPresent();
    }

    public Operator changeResponsibility(Operator operator, Operator promotedOperator, String responsibility){
        if(operator.getResponsibility().getResponsibility() != Responsibility.ADMIN.getResponsibility()){
            throw new UsernameAlreadyExistsException(); //criar erro personalizado
        }
        else if(responsibility.equalsIgnoreCase(Responsibility.ADMIN.getResponsibility())){
            promotedOperator.setResponsibility(Responsibility.ADMIN);
            return promotedOperator;
        }
        else if(responsibility.equalsIgnoreCase(Responsibility.SALESMAN.getResponsibility())){
            promotedOperator.setResponsibility(Responsibility.SALESMAN);
            return promotedOperator;
        }
        else if(responsibility.equalsIgnoreCase(Responsibility.STOCKHOLDER.getResponsibility())){
            promotedOperator.setResponsibility(Responsibility.STOCKHOLDER);
            return promotedOperator;
        }
        return promotedOperator;
    }

    public Operator findById(Long id){
        return operatorRepository.findById(id).get();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Operator operator = operatorRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Name inválido"));

        String[] roles = new String[]{"USER"};

        return User.builder().username(operator.getUsername()).password(operator.getPassword()).roles().build();
    }

    public UserDetails authentic(Operator operator){
        UserDetails usuario = loadUserByUsername(operator.getUsername());
        boolean passwordEquals = encoder.matches(operator.getPassword(), usuario.getPassword());
        if(passwordEquals){
            return usuario;
        }
        throw new PasswordInvalidException();
    }
}
