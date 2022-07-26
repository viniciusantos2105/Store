package br.com.store.entites;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Insira um nome válido")
    private String name;
    @NotBlank(message = "Insira quantida válida")
    private Integer quantity;
    @NotBlank(message = "Insira preço válido")
    private BigDecimal price;

}
