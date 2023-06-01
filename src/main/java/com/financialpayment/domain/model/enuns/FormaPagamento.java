package com.financialpayment.domain.model.enuns;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.io.Serializable;

@Getter
@JsonFormat(shape=JsonFormat.Shape.OBJECT)
public enum FormaPagamento implements Serializable {
    CHEQUE("Cheque"),
    DINHEIRO("Dinheiro"),
    VALE_REFEICAO("Vale Refeição"),
    VALE_ALIMENTACAO("Vale Alimentação"),
    CARTAO_DEBITO_SANTANDER("Cartão de Débito Santander"),
    CARTAO_DEBITO_NUBANK("Cartão de Debito Nubank"),
    CARTAO_DEBITO_ORIGINAL("Cartão de Debito Original"),
    CARTAO_CRED_BCO_SANTANDER("Cartão de Crédito Santander"),

    DEB_NETB_BCO_SANTANER("Débito NetBanking Conta Santander"),
    DEB_NETB_BCO_BRADESCO_PJ("Débito NetBanking Conta Bradesco PJ"),
    DEB_NETB_BCO_ORIGINAL("Débito NetBanking Banco Original"),
    DEB_NETB_BCO_NUBANK("Débito NetBanking Conta Nubank"),

    DEB_AUTO_SANTANDER("Débito Automático Santander"),

    PGTO_BOCA_CAIXA("Pagamento Boca do Caixa"),
    PGTO_CAIXA_ELETRONICO("Pagamento Caixa Eletônico");

    private final String nome;
    private final String value;

    FormaPagamento(String nome) {
        this.nome = nome;
        this.value = this.toString();
    }

    @JsonCreator
    public static FormaPagamento forValues(@JsonProperty("value") String value) {
        for (FormaPagamento formaPagamento : FormaPagamento.values()) {
            if (formaPagamento.value.equals(value)) {
                return formaPagamento;
            }
        }
        return null;
    }
}
