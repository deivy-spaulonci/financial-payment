package com.financialpayment.domain.model.enuns;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonFormat(shape=JsonFormat.Shape.OBJECT)
public enum ContaStatus {
    ABERTO("Em aberto"),
    HOJE("Vencimento Hoje"),
    ATRASADO("Atrasado"),
    CANCELADO("Cancelado e substituido"),
    PAGO("Pago");

    private final String nome;
    private final String value;

    ContaStatus(String nome) {
        this.nome = nome;
        this.value = this.toString();
    }

    @JsonCreator
    public static ContaStatus forValues(@JsonProperty("value") String value) {
        for (ContaStatus contaStatus : ContaStatus.values()) {
            if (contaStatus.value.equals(value)) {
                return contaStatus;
            }
        }
        return null;
    }
}
