package br.com.store.enums;

import lombok.Getter;

@Getter
public enum Responsibility {

    ADMIN(1, "Admin"),
    SALESMAN(2, "Vendedor"),
    STOCKHOLDER(3, "Estoquista");

    private Integer option;
    private String responsibility;

    Responsibility(Integer option, String responsibility) {
        this.option = option;
        this.responsibility = responsibility;
    }
}
