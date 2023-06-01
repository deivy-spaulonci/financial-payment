package com.financialpayment.domain.repsitory;

import com.financialpayment.domain.model.Cidade;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CidadeRepository extends BaseRepository<Cidade, Long> {
    List<Cidade> findCidadesByNomeIsContainingIgnoreCase(String nome);
}
