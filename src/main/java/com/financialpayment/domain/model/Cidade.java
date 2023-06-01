package com.financialpayment.domain.model;

import com.financialpayment.domain.model.enuns.Estado;
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
@Table(name = "CIDADE")
public class Cidade implements Serializable {
    @Id
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Enumerated(EnumType.STRING)
    @Column(length = 2, nullable = false)
    private Estado estado;
}
