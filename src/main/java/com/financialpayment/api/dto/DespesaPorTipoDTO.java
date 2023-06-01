package com.financialpayment.api.dto;

import com.financialpayment.domain.model.enuns.TipoDespesa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DespesaPorTipoDTO {

    private TipoDespesa tipoDespesa;

    private BigDecimal valor;

    private LocalDate dataInicio;
    private LocalDate dataFim;
}
