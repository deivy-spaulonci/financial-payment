package com.financialpayment.business.service;


import com.financialpayment.business.filter.CidadeFilter;
import com.financialpayment.domain.model.Cidade;
import com.financialpayment.domain.model.QCidade;
import com.financialpayment.domain.repsitory.CidadeRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    private BooleanBuilder createPredicateCidade(CidadeFilter cidadeFilter){
        QCidade qCidade = QCidade.cidade;
        BooleanBuilder builder = new BooleanBuilder();

        if(Objects.nonNull(cidadeFilter.nome) && !cidadeFilter.nome.isEmpty())
            builder.and(qCidade.nome.lower().containsIgnoreCase(cidadeFilter.nome.toLowerCase()));

        if(Objects.nonNull(cidadeFilter.estado) && Objects.nonNull(cidadeFilter.getEstado().getValue()))
            builder.and(qCidade.estado.eq(cidadeFilter.estado));

        return builder;
    }

    public List<Cidade> list(CidadeFilter cidadeFilter, Sort sort) {
        Iterable<Cidade> cidadeIterable = cidadeRepository.findAll(createPredicateCidade(cidadeFilter), sort);
        return StreamSupport.stream(cidadeIterable.spliterator(), false).collect(Collectors.toList());
    }

    public List<Cidade> listByNome(String nome){
        return cidadeRepository.findCidadesByNomeIsContainingIgnoreCase(nome);
    }
}
