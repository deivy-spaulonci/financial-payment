package com.financialpayment.business.filter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.financialpayment.domain.model.Fornecedor;
import com.financialpayment.domain.model.enuns.FormaPagamento;
import com.financialpayment.domain.model.enuns.TipoDespesa;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DespesaFilter implements Serializable {
    @JsonProperty("id")
    public Long id;

    @JsonProperty("tipoDespesa")
    public TipoDespesa tipoDespesa;

    @JsonProperty("dataInicial")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate dataInicial;

    @JsonProperty("dataFinal")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate dataFinal;

    @JsonProperty("fornecedor")
    public Fornecedor fornecedor;

    @JsonProperty("formaPagamento")
    public FormaPagamento formaPagamento;
}
