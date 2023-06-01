package com.financialpayment.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.financialpayment.domain.model.Fornecedor;
import com.financialpayment.domain.model.enuns.FormaPagamento;
import com.financialpayment.domain.model.enuns.TipoDespesa;
import com.opencsv.bean.CsvDate;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DespesaDTO {
    private Long id;

    @NotNull
    private TipoDespesa tipoDespesa;

    @NotNull
    private Fornecedor fornecedor;

    @CsvDate(value = "yyyy-MM-dd")
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate data;

    @NotNull
    private FormaPagamento formaPagamento;

    @NotNull
    private BigDecimal valor;

    private String obs;

    private LocalDateTime dataLancamento;

    private String ajusteFornecedor;
}
