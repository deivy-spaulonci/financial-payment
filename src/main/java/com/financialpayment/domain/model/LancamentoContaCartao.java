package com.financialpayment.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "LANCAMENTO_CONTA_CARTAO")
public class LancamentoContaCartao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_id_lancamentocontacartao")
    @SequenceGenerator(name = "sequence_id_lancamentocontacartao", sequenceName = "sequence_lancamentocontacartao", allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "FORNECEDOR_ID", nullable = true)
    private Fornecedor fornecedor;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal valor;

    @Column(length = 10, nullable = false)
    private int parcela;

    @Column(name = "TOTAL_PARCELAS", length = 10, nullable = false)
    private int totalParcela;
}

