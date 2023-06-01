package com.financialpayment.domain.repsitory;

import com.financialpayment.domain.model.Fornecedor;
import org.springframework.stereotype.Repository;

@Repository
public interface FornecedorRepository  extends BaseRepository<Fornecedor, Long> {
    Fornecedor findByCnpj(String cnpj);
}
