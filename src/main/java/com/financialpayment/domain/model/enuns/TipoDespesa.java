package com.financialpayment.domain.model.enuns;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.io.Serializable;

@Getter
@JsonFormat(shape=JsonFormat.Shape.OBJECT)
public enum TipoDespesa implements Serializable {

    ALIMENTACAO("Alimentação"),
    BALSA("Balsa"),
    CINEMA("Cinema"),
    COMBUSTIVEL("Combustivel"),
    DPVAT("DPVAT"),
    DIVERSOS("Diversos"),
    ESTACIONAMENTO("Estacionamento"),
    FARMACIA("Fármacia"),
    HARDWARE_PC("HardWare PC"),
    HOSPEDAGEM("Hospedagem"),
    LAZER("Lazer"),
    LICENCIAMENTO("Licenciamento"),
    MANUTENCAO_AUTOMOVEL("Manutenção Automovel"),
    PASSAGEM("Passagem"),
    PEDAGIO("Pedágio"),
    PESCARIA("Pescaria"),
    PRESENTE("Presente"),
    RECARGA_CELULAR("Recarga TIM"),
    SUPERMERCADO("Supermercado"),
    TRANSPORTE("Transporte"),
    VESTUARIO("Vestuário"),
    MEDICO("Medico/Consulta");

    private final String nome;
    private final String value;

    TipoDespesa(String nome) {
        this.nome = nome;
        this.value = this.toString();
    }

    @JsonCreator
    public static TipoDespesa forValues(@JsonProperty("value") String value) {
        for (TipoDespesa tipoDespesa : TipoDespesa.values()) {
            if (tipoDespesa.value.equals(value)) {
                return tipoDespesa;
            }
        }
        return null;
    }
}
