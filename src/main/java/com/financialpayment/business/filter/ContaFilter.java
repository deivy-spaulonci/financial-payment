package com.financialpayment.business.filter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.financialpayment.domain.model.TipoConta;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContaFilter implements Serializable {
    @JsonProperty("id")
    public Long id;

    @JsonProperty("codigoBarra")
    public String codigoBarra;

    @JsonProperty("tipoConta")
    public TipoConta tipoConta;

    @JsonProperty("vencimentoInicial")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate vencimentoInicial;

    @JsonProperty("vencimentoFinal")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate vencimentoFinal;

    @JsonProperty("emissaoInicial")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate emissaoInicial;

    @JsonProperty("emissaoFinal")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate emissaoFinal;

    @JsonProperty("intStatus")
    public Integer intStatus;
}
