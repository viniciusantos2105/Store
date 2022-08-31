package br.com.store.entites;

import br.com.store.enums.Responsibility;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Operator implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Campo username é obrigatorio")
    private String username;
    @NotNull(message = "Campo senha é obrigatorio")
    private String password;
    @NotNull(message = "Campo cargo é obrigatorio")
    private Responsibility responsibility;
}
