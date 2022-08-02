package br.com.store.entites;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Campo username é obrigatorio")
    private String username;
    @NotBlank(message = "Campo nome é obrigatorio")
    private String name;
    @CPF(message = "Informe CPF válido")
    @NotBlank(message = "Campo CPF é obrigatorio")
    private String cpf;
    @NotBlank(message = "Campo password é obrigatorio")
    private String password;

}
