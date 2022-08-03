package br.com.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDTO {

    private Long id;
    private Long id2;
    private Integer quantity;
    private String address;
    private String number;
}
