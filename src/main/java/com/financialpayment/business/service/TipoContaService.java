package com.financialpayment.business.service;

import com.financialpayment.business.filter.TipoContaFilter;
import com.financialpayment.domain.model.QTipoConta;
import com.financialpayment.domain.model.TipoConta;
import com.financialpayment.domain.repsitory.TipoContaRepository;
import com.querydsl.core.BooleanBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TipoContaService {
    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private TipoContaRepository tipoContaRepository;

    public TipoConta create(TipoConta tipoConta) {
        return tipoContaRepository.saveAndFlush(tipoConta);
    }

    public List<TipoConta> list(TipoContaFilter tipoContaFilter, Sort sort) {
        Iterable<TipoConta> despesaIterable = tipoContaRepository.findAll(cratePredicateTipoConta(tipoContaFilter), sort);
        return StreamSupport.stream(despesaIterable.spliterator(), false).collect(Collectors.toList());
    }

    public TipoConta update(TipoConta tipoConta) {
        return this.tipoContaRepository.save(tipoConta);
    }

    public Optional<TipoConta> findById(Long id) {
        return this.tipoContaRepository.findById(id);
    }

    private BooleanBuilder cratePredicateTipoConta(TipoContaFilter tipoContaFilter){
        QTipoConta qTipoConta = QTipoConta.tipoConta;
        BooleanBuilder builder = new BooleanBuilder();

        if(tipoContaFilter.id != null){
            builder.and(qTipoConta.id.eq(tipoContaFilter.getId()));
        }
        if (Objects.nonNull(tipoContaFilter.nome) && !tipoContaFilter.nome.isEmpty()) {
            builder.and(qTipoConta.nome.containsIgnoreCase(tipoContaFilter.nome));
        }
        if(Objects.nonNull(tipoContaFilter.contaCartao)){
            builder.and(qTipoConta.contaCartao.eq(tipoContaFilter.contaCartao));
        }
        if (Objects.nonNull(tipoContaFilter.tipoContaStatus)) {
            builder.and(qTipoConta.tipoContaStatus.eq(tipoContaFilter.tipoContaStatus));
        }
        return builder;
    }
}
