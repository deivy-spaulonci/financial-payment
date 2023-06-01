package com.financialpayment.domain.model.enuns;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.io.Serializable;

@Getter
@JsonFormat(shape=JsonFormat.Shape.OBJECT)
public enum Estado implements Serializable {
    RO("Rondônia"),
    AC("Acre"),
    AM("Amazonas"),
    RR("Roraima"),
    PA("Pará"),
    AP("Amapá"),
    TO("Tocantins"),
    MA("Maranhão"),
    PI("Piauí"),
    CE("Ceará"),
    RN("Rio Grande do Norte"),
    PB("Paraíba"),
    PE("Pernambuco"),
    AL("Alagoas"),
    SE("Sergipe"),
    BA("Bahia"),
    MG("Minas Gerais"),
    ES("Espírito Santo"),
    RJ("Rio de Janeiro"),
    SP("São Paulo"),
    PR("Paraná"),
    SC("Santa Catarina"),
    RS("Rio Grande do Sul"),
    MS("Mato Grosso do Sul"),
    MT("Mato Grosso"),
    GO("Goiás"),
    DF("Distrito Federal");

    private final String nome;
    private final String value;

    Estado(String nome) {
        this.nome = nome;
        this.value = this.toString();
    }

    @JsonCreator
    public static Estado forValues(@JsonProperty("value") String value) {
        for (Estado estado : Estado.values()) {
            if (estado.value.equals(value)) {
                return estado;
            }
        }
        return null;
    }

}
