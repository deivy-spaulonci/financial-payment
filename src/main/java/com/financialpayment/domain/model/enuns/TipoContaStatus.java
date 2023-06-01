package com.financialpayment.domain.model.enuns;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.io.Serializable;

@Getter
@JsonFormat(shape=JsonFormat.Shape.OBJECT)
public enum TipoContaStatus implements Serializable {
    ATIVO("Ativa"),
    FINALIZADO("Finalizada"),
    CANCELADO("Cancelada");

    private final String nome;
    private final String value;

    TipoContaStatus(String nome) {
        this.nome = nome;
        this.value = this.toString();
    }

    @JsonCreator
    public static TipoContaStatus forValues(@JsonProperty("value") String value) {
        for (TipoContaStatus tipoContaStatus : TipoContaStatus.values()) {
            if (tipoContaStatus.value.equals(value)) {
                return tipoContaStatus;
            }
        }
        return null;
    }
}
