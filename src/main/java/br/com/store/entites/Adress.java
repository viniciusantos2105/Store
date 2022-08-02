package br.com.store.entites;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Adress {

    @NotBlank
    private String cep;
    @NotBlank
    private String number;
}
