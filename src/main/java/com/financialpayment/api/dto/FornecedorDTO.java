package com.financialpayment.api.dto;

import com.financialpayment.domain.model.Cidade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class FornecedorDTO {
    private Long id;

    @Size(min = 3)
    @NotBlank
    private String nome;

    @Size(min = 3)
    @NotBlank
    private String razaoSocial;

    private String cnpj;

    private Cidade cidade;

}
