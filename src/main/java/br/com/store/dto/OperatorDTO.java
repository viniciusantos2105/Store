package br.com.store.dto;

import br.com.store.enums.Responsibility;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperatorDTO {

    private Long id;

    private String username;

    private Responsibility responsibility;

    private String password;
}
