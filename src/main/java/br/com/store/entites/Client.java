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

    public Client(Long id, String username, String name, String cpf, String email, String password, Address address, List<Request> purchaseRecord) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.password = password;
        this.address = address;
        this.purchaseRecord = purchaseRecord;
    }
}
