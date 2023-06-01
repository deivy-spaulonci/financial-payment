package com.financialpayment.business.filter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.financialpayment.domain.model.enuns.Estado;
import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CidadeFilter implements Serializable {

    @JsonProperty("nome")
    public String nome;

    @JsonProperty("estado")
    public Estado estado;

}
