package br.com.store.entites;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class  Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Campo nome é obrigatorio")
    private String name;
    @NotNull(message = "Campo quantidade é obrigatorio")
    private Integer quantity;
    @NotNull(message = "Campo preço é obrigatorio")
    private BigDecimal price;

}
