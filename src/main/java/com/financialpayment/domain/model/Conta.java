package com.financialpayment.domain.model;

import com.financialpayment.domain.model.enuns.FormaPagamento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CONTA")
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_id_conta")
    @SequenceGenerator(name = "sequence_id_conta", sequenceName = "sequence_conta", allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(length = 60, nullable = false)
    private String numero;

    @Column(name = "CODIGO_BARRA", length = 60, nullable = false)
    private String codigoBarra;

    @ManyToOne(optional = false)
    @JoinColumn(name = "TIPO_CONTA_ID")
    private TipoConta tipoConta;

    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate emissao;

    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate vencimento;

    @Column(length = 10, nullable = false)
    private int parcela;

    @Column(name = "TOTAL_PARCELAS", length = 10)
    private int totalParcela;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal valor;

    //PAGAMENTO/////////////////////////////////////////////////////////////////
    @Column(columnDefinition = "DATE")
    private LocalDate dataPagamento;

    @Enumerated(EnumType.STRING)
    @Column(length = 60, nullable = true)
    private FormaPagamento formaPagamento;

    @Column(name = "VALOR_PAGO", precision = 10, scale = 2, nullable = true)
    private BigDecimal valorPago;

    //CANCELAMENTO
    private Boolean cancelado;

    @Column(name = "ID_CANCELAMENTO")
    private Long idCancelamento;

    @Column(length = 255, nullable = true)
    private String obs;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinTable(name = "CONTA_LANCAMENTO_CONTA_CARTAO",
            joinColumns = @JoinColumn(name = "CONTA_ID"),
            inverseJoinColumns = @JoinColumn(name = "LANCAMENTO_CONTA_CARTAO_ID"))
    private List<LancamentoContaCartao> lancamentoContaCartao;

    @Column(name = "DATA_LANCAMENTO",
            insertable = false, nullable = true,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dataLancamento;

    @Transient
    private String status;
}
