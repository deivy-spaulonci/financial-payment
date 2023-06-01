package com.financialpayment.business.filter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.financialpayment.domain.model.enuns.TipoContaStatus;
import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipoContaFilter implements Serializable {

    @JsonProperty("id")
    public Long id;

    @JsonProperty("nome")
    public String nome;

    @JsonProperty("contaCartao")
    public Boolean contaCartao;

    @JsonProperty("contaCartao")
    public TipoContaStatus tipoContaStatus;
}
