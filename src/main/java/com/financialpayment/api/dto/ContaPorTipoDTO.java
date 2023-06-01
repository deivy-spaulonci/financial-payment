package com.financialpayment.api.dto;

import com.financialpayment.domain.model.TipoConta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContaPorTipoDTO {

    private TipoConta tipoConta;

    private BigDecimal valor;
}
