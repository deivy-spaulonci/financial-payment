package com.financialpayment.domain.model;

import com.financialpayment.domain.model.enuns.TipoContaStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TIPO_CONTA")
public class TipoConta implements Serializable {

    @Id
    private Long id;

    @Column(length = 60, nullable = false, unique= true)
    private String nome;

    @Column(name = "CONTA_CARTAO")
    private Boolean contaCartao;

    @Enumerated(EnumType.STRING)
    @Column(length = 60, nullable = false)
    private TipoContaStatus tipoContaStatus;
}
