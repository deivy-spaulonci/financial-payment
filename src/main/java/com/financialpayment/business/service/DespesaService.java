package com.financialpayment.business.service;

import com.financialpayment.api.dto.DespesaImportacaoDTO;
import com.financialpayment.business.filter.DespesaFilter;
import com.financialpayment.domain.model.Despesa;
import com.financialpayment.domain.model.Fornecedor;
import com.financialpayment.domain.model.QDespesa;
import com.financialpayment.domain.model.enuns.TipoDespesa;
import com.financialpayment.domain.repsitory.DespesaRepository;
import com.financialpayment.domain.repsitory.FornecedorRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DespesaService {

    @PersistenceContext
    private EntityManager manager;
    @Autowired
    DespesaRepository despesaRepository;
    @Autowired
    FornecedorRepository fornecedorRepository;

    @Autowired
    FornecedorService fornecedorService;

    public Despesa create(Despesa despesa) {
        return despesaRepository.save(despesa);
    }

    public Despesa update(Despesa despesa) {
        return despesaRepository.save(despesa);
    }

    public Optional<Despesa> findById(Long id) {
        return despesaRepository.findById(id);
    }

    public void deleteById(Long id) {
        despesaRepository.deleteById(id);
    }

    public List<Despesa> searchByFilterOrder(DespesaFilter despesaFilter, Sort sort) {
        Iterable<Despesa> despesaIterable = despesaRepository.findAll(createPrecicateDespesa(despesaFilter), sort);
        return StreamSupport.stream(despesaIterable.spliterator(), false).collect(Collectors.toList());
    }

    public Page<Despesa> getPageByFilters(DespesaFilter despesaFilter, Pageable pageable) {
        return despesaRepository.findAll(createPrecicateDespesa(despesaFilter), pageable);
    }

    public List<Despesa> findAllSort(String order, String campo){
        return despesaRepository.findAll(Sort.by(order.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, campo));
    }

    public BigDecimal getValorTotal(DespesaFilter despesaFilter){
        JPAQuery query = new JPAQuery(this.manager);
        QDespesa qDespesa = QDespesa.despesa;
        query.select(qDespesa.valor.sum()).from(qDespesa).where(createPrecicateDespesa(despesaFilter));
        return query.fetchOne() != null ? new BigDecimal(query.fetchOne().toString()) : BigDecimal.ZERO;
    }

    public void importarDespesaCVS(List<DespesaImportacaoDTO> despesaImportacaoDTOList){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        Fornecedor fornecedorVazio = fornecedorRepository.findById(43l).get();
        for(DespesaImportacaoDTO despesaImportacaoDTO : despesaImportacaoDTOList){
            BigDecimal valor =  new BigDecimal(despesaImportacaoDTO.getValor().replace(".","").replace(",","."));

            Despesa despesa = Despesa.builder()
                    .tipoDespesa(despesaImportacaoDTO.getTipoDespesa())
                    .data(LocalDate.parse(despesaImportacaoDTO.getData(), formatter))
                    .formaPagamento(despesaImportacaoDTO.getFormaPagamento())
                    .obs(despesaImportacaoDTO.getObs())
                    .valor(valor)
                    .fornecedor(fornecedorVazio)
                    .ajusteFornecedor(despesaImportacaoDTO.getAjusteFornecedor())
                    .build();

            despesaRepository.save(despesa);
        }
    }

    public Long getTotalLinhas(DespesaFilter despesaFilter){
        JPAQuery query = new JPAQuery(this.manager);
        QDespesa qDespesa = QDespesa.despesa;
        query.select(qDespesa.count()).from(qDespesa).where(createPrecicateDespesa(despesaFilter));
        return query.fetchOne() != null ? Long.parseLong(query.fetchOne().toString()) : 0L;
    }

    public List getDespesasPorTipo(){
        JPAQuery query = new JPAQuery(this.manager);
        JPAQuery submax = new JPAQuery(this.manager);
        JPAQuery submin = new JPAQuery(this.manager);
        QDespesa qDespesa = QDespesa.despesa;

        query.select(qDespesa.tipoDespesa,
                        qDespesa.valor.sum(),
                        submax.select(qDespesa.data.max()).from(qDespesa),
                        submin.select(qDespesa.data.min()).from(qDespesa)
                ).from(qDespesa).
                distinct().
                groupBy(qDespesa.tipoDespesa);

        List resultado  = query.fetch();

        return resultado;
    }

    private BooleanBuilder createPrecicateDespesa(DespesaFilter despesaFilter) {
        QDespesa qDespesa = QDespesa.despesa;
        BooleanBuilder builder = new BooleanBuilder();
        if (despesaFilter.id != null) {
            builder.and(qDespesa.id.eq(despesaFilter.id));
        }
        if (Objects.nonNull(despesaFilter.tipoDespesa)) {
            builder.and(qDespesa.tipoDespesa.eq(despesaFilter.tipoDespesa));
        }
        if (Objects.nonNull(despesaFilter.dataInicial) && Objects.nonNull(despesaFilter.dataFinal)) {
            builder.and(qDespesa.data.between(despesaFilter.dataInicial, despesaFilter.dataFinal));
        } else if (Objects.nonNull(despesaFilter.dataInicial)) {
            builder.and(qDespesa.data.gt(despesaFilter.dataInicial));
        } else if (Objects.nonNull(despesaFilter.dataFinal)){
            builder.and(qDespesa.data.lt(despesaFilter.dataFinal));
        }
        if(Objects.nonNull(despesaFilter.fornecedor)){
            builder.and(qDespesa.fornecedor.id.eq(despesaFilter.fornecedor.getId()));
        }
        if (Objects.nonNull(despesaFilter.formaPagamento)) {
            builder.and(qDespesa.formaPagamento.eq(despesaFilter.formaPagamento));
        }
        return builder;
    }
}
