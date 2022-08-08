package br.com.store.repository;

import br.com.store.entites.Client;
import br.com.store.entites.Operator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OperatorRepository extends JpaRepository<Operator, Long> {

    Optional<Operator> findByUsername(String username);

}
