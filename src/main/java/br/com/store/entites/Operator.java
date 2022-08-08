package br.com.store.entites;

import br.com.store.enums.Responsibility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Operator implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Campo username é obrigatorio")
    private String username;
    @NotBlank(message = "Campo senha é obrigatorio")
    private String password;
    @NotBlank(message = "Campo cargo é obrigatorio")
    private Responsibility responsibility;
}
