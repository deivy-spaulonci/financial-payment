package com.financialpayment.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "FORNECEDOR")
public class Fornecedor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_id_fornecedor")
    @SequenceGenerator(name = "sequence_id_fornecedor", sequenceName = "sequence_fornecedor", allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(length = 255, nullable = false)
    private String nome;

    @Column(name = "RAZAO_SOCIAL", length = 255, nullable = false)
    private String razaoSocial;

    @Column(length = 60, nullable = true)
    private String cnpj;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CIDADE_ID")
    private Cidade cidade;
}
