package br.com.store.entites;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
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
    @Email
    private String email;
    @NotBlank(message = "Campo password é obrigatorio")
    private String password;
    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "tb_address")
    private Address address;

    @OneToMany(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "tb_purchaseRecord")
    @JsonManagedReference
    private List<Request> purchaseRecord = new ArrayList<>();

}
