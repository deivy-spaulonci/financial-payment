package com.financialpayment.api.dto;


import com.financialpayment.domain.model.enuns.FormaPagamento;
import com.financialpayment.domain.model.enuns.TipoDespesa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DespesaImportacaoDTO {
    private Long id;

    private TipoDespesa tipoDespesa;

    private String data;

    private FormaPagamento formaPagamento;

    private String valor;

    private String obs;

    private String ajusteFornecedor;


}
