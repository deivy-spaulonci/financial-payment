package com.financialpayment.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.financialpayment.domain.model.LancamentoContaCartao;
import com.financialpayment.domain.model.TipoConta;
import com.financialpayment.domain.model.enuns.ContaStatus;
import com.financialpayment.domain.model.enuns.FormaPagamento;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContaDTO {

    private Long id;

    private String numero;

    @NotNull
    private String codigoBarra;

    @NotNull
    private TipoConta tipoConta;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate emissao;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate vencimento;

    private int parcela;

    private int totalParcela;

    @NotNull
    private BigDecimal valor;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataPagamento;

    private FormaPagamento formaPagamento;

    private BigDecimal valorPago;

    private Boolean cancelado;

    private Long idCancelamento;

    private String obs;

    private List<LancamentoContaCartao> lancamentoContaCartao;

    @JsonGetter(value = "status")
    public String getStatus() {
        switch (getIntStatus()) {
            case 1:
                return ContaStatus.ABERTO.getNome();
            case 0:
                return ContaStatus.HOJE.getNome();
            case -1:
                return ContaStatus.ATRASADO.getNome();
            case 2:
                return ContaStatus.PAGO.getNome();
            case 3:
                return ContaStatus.CANCELADO.getNome();
            default:
                return ContaStatus.PAGO.getNome();
        }
    }

    @JsonGetter(value = "intStatus")
    public int getIntStatus() {
        if(Objects.nonNull(getCancelado()) && getCancelado()){
            return 3;
        }
        if (Objects.isNull(getDataPagamento())) {
            if(getVencimento().equals(LocalDate.now())){
                return 0;
            }else if(getVencimento().isBefore(LocalDate.now())){
                return -1;
            }else if(getVencimento().isAfter(LocalDate.now())){
                return 1;
            }
        }
        return 2;
    }
}
