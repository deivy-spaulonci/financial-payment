package com.financialpayment.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.financialpayment.domain.model.enuns.Estado;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CidadeDTO implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("estado")
    private Estado estado;
}
