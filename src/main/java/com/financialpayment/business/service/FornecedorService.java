package com.financialpayment.business.service;

import com.financialpayment.business.filter.FornecedorFilter;
import com.financialpayment.domain.model.Fornecedor;
import com.financialpayment.domain.model.QFornecedor;
import com.financialpayment.domain.repsitory.FornecedorRepository;
import com.querydsl.core.BooleanBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class FornecedorService {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    public Fornecedor create(Fornecedor fornecedor) {
        return fornecedorRepository.save(fornecedor);
    }

    public List<Fornecedor> searchByFilterOrder(FornecedorFilter fornecedorFilter, Sort sort) {
        Iterable<Fornecedor> fornecedorIterable = fornecedorRepository.findAll(createPrecicateFornecedor(fornecedorFilter), sort);
        return StreamSupport.stream(fornecedorIterable.spliterator(), false).collect(Collectors.toList());
    }

    public Page<Fornecedor> getPageByFilters(FornecedorFilter fornecedorFilter, Pageable pageable) {
        return fornecedorRepository.findAll(createPrecicateFornecedor(fornecedorFilter), pageable);
    }

    public Fornecedor update(Fornecedor fornecedor) {
        return fornecedorRepository.save(fornecedor);
    }

    public Optional<Fornecedor> findById(Long id) {
        return fornecedorRepository.findById(id);
    }

    public void deleteById(Long id) {
        fornecedorRepository.deleteById(id);
    }


    public List<Fornecedor> searchStringFornecedor(String s){
        QFornecedor qFornecedor = QFornecedor.fornecedor;
        BooleanBuilder builder = new BooleanBuilder();
        builder.or(qFornecedor.nome.containsIgnoreCase(s));
        builder.or(qFornecedor.razaoSocial.containsIgnoreCase(s));
        builder.or(qFornecedor.cnpj.containsIgnoreCase(s));
        Iterable<Fornecedor> fornecedorIterable = fornecedorRepository.findAll(builder,Sort.by(Sort.Direction.ASC, "nome"));
        return StreamSupport.stream(fornecedorIterable.spliterator(), false).collect(Collectors.toList());
    }
    private BooleanBuilder createPrecicateFornecedor(FornecedorFilter fornecedorFilter) {
        QFornecedor qFornecedor = QFornecedor.fornecedor;
        BooleanBuilder builder = new BooleanBuilder();
        if (fornecedorFilter.id != null) {
            builder.and(qFornecedor.id.eq(fornecedorFilter.id));
        }
        if (Objects.nonNull(fornecedorFilter.nome) && !fornecedorFilter.nome.isEmpty()) {
            builder.and(qFornecedor.nome.containsIgnoreCase(fornecedorFilter.nome));
        }
        if (Objects.nonNull(fornecedorFilter.razaoSocial) && !fornecedorFilter.razaoSocial.isEmpty()) {
            builder.and(qFornecedor.razaoSocial.containsIgnoreCase(fornecedorFilter.razaoSocial));
        }
        if (Objects.nonNull(fornecedorFilter.cnpj) && !fornecedorFilter.cnpj.isEmpty()) {
            builder.and(qFornecedor.cnpj.containsIgnoreCase(fornecedorFilter.cnpj));
        }
        if (Objects.nonNull(fornecedorFilter.cidade) && Objects.nonNull(fornecedorFilter.cidade.getNome())) {
            builder.and(qFornecedor.cidade.nome.containsIgnoreCase(fornecedorFilter.cidade.getNome()));
        }
        if (Objects.nonNull(fornecedorFilter.cidade) && Objects.nonNull(fornecedorFilter.cidade.getId())) {
            builder.and(qFornecedor.cidade.eq(fornecedorFilter.cidade));
        }
        return builder;
    }
}
