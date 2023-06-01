package com.financialpayment.business.filter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.financialpayment.domain.model.Cidade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FornecedorFilter implements Serializable {
    @JsonProperty("id")
    public Long id;

    @JsonProperty("nome")
    public String nome;

    @JsonProperty("razaoSocial")
    public String razaoSocial;

    @JsonProperty("cnpj")
    public String cnpj;

    @JsonProperty("cidade")
    public Cidade cidade;
}
