package com.financialpayment.domain.model;

import com.financialpayment.domain.model.enuns.FormaPagamento;
import com.financialpayment.domain.model.enuns.TipoDespesa;
import com.opencsv.bean.CsvDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DESPESA")
public class Despesa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_id_despesa")
    @SequenceGenerator(name = "sequence_id_despesa", sequenceName = "sequence_despesa", allocationSize = 1, initialValue = 1)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 60, nullable = false)
    private TipoDespesa tipoDespesa;

    @ManyToOne
    @JoinColumn(name = "FORNECEDOR_ID", nullable = true)
    private Fornecedor fornecedor;

    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate data;

    @Enumerated(EnumType.STRING)
    @Column(length = 60, nullable = false)
    private FormaPagamento formaPagamento;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal valor;

    @Column(length = 255, nullable = true)
    private String obs;

    @Column(name = "DATA_LACAMENTO", insertable = false, nullable = true, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dataLancamento;

    @Column(length = 255, nullable = true)
    private String ajusteFornecedor;
}
