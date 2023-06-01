package com.financialpayment.business.service;

import com.financialpayment.business.filter.ContaFilter;
import com.financialpayment.domain.model.Conta;
import com.financialpayment.domain.model.QConta;
import com.financialpayment.domain.model.QTipoConta;
import com.financialpayment.domain.repsitory.ContaRepository;
import com.financialpayment.domain.repsitory.DespesaRepository;
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
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ContaService {

    @PersistenceContext
    private EntityManager manager;
    @Autowired
    ContaRepository contaRepository;

    public Conta create(Conta conta) {
        return contaRepository.save(conta);
    }

    public Conta update(Conta conta) {
        return contaRepository.save(conta);
    }

    public void deleteById(Long id) {
        contaRepository.deleteById(id);
    }

    public long count() {
        return contaRepository.count();
    }

    public Optional<Conta> findById(Long id) {
        return contaRepository.findById(id);
    }

    public List<Conta> searchByFilterOrder(ContaFilter contaFilter, Sort sort) {
        Iterable<Conta> contaIterable = contaRepository.findAll(createPrecicateConta(contaFilter), sort);
        return StreamSupport.stream(contaIterable.spliterator(), false).collect(Collectors.toList());
    }

    public Page<Conta> getPageByFilters(ContaFilter contaFilter, Pageable pageable) {
        return contaRepository.findAll(createPrecicateConta(contaFilter), pageable);
    }

    public BigDecimal getValorTotal(ContaFilter contaFilter){
        JPAQuery query = new JPAQuery(this.manager);
        QConta qConta = QConta.conta;
        query.from(qConta).select(qConta.valor.sum()).where(createPrecicateConta(contaFilter));
        return  query.fetchOne() != null ? new BigDecimal(query.fetchOne().toString()) : BigDecimal.ZERO;
    }

    public List getContasPorTipo(){
        JPAQuery query = new JPAQuery(this.manager);
        QConta qConta = QConta.conta;
        QTipoConta qTipoConta = QTipoConta.tipoConta;

        query.select(qTipoConta.nome,
                        qConta.valor.sum()
                ).from(qConta).
                innerJoin(qConta.tipoConta, qTipoConta).
                distinct().
                groupBy(qTipoConta.nome);

        List resultado  = query.fetch();

        return resultado;
    }

    private BooleanBuilder createPrecicateConta(ContaFilter contaFilter) {
        QConta qConta = QConta.conta;
        BooleanBuilder builder = new BooleanBuilder();
        if (contaFilter.id != null) {
            builder.and(qConta.id.eq(contaFilter.id));
        }
        if (Objects.nonNull(contaFilter.codigoBarra) && !contaFilter.codigoBarra.isEmpty()) {
            builder.and(qConta.codigoBarra.like("%" + contaFilter.getCodigoBarra() + "%"));
        }
        if (Objects.nonNull(contaFilter.tipoConta) && Objects.nonNull(contaFilter.tipoConta.getId())) {
            builder.and(qConta.tipoConta.id.eq(contaFilter.tipoConta.getId()));
        }
        if (Objects.nonNull(contaFilter.vencimentoInicial) && Objects.nonNull(contaFilter.vencimentoFinal)) {
            builder.and(qConta.vencimento.between(contaFilter.vencimentoInicial, contaFilter.vencimentoFinal));
        } else if (Objects.nonNull(contaFilter.vencimentoInicial)) {
            builder.and(qConta.vencimento.gt(contaFilter.getVencimentoInicial()));
        } else if (Objects.nonNull(contaFilter.vencimentoFinal)) {
            builder.and(qConta.vencimento.lt(contaFilter.getVencimentoFinal()));
        }
        if (Objects.nonNull(contaFilter.emissaoInicial) && Objects.nonNull(contaFilter.emissaoFinal)) {
            builder.and(qConta.emissao.between(contaFilter.vencimentoInicial, contaFilter.vencimentoFinal));
        } else if (Objects.nonNull(contaFilter.emissaoInicial)) {
            builder.and(qConta.emissao.gt(contaFilter.emissaoInicial));
        } else if (Objects.nonNull(contaFilter.emissaoInicial)) {
            builder.and(qConta.emissao.lt(contaFilter.emissaoFinal));
        }

        if (Objects.nonNull(contaFilter.intStatus))
            switch (contaFilter.intStatus) {
                case 3:
                    builder.and(qConta.cancelado.isTrue());
                    break;
                case 2:
                    builder.and(qConta.dataPagamento.isNotNull()).and(qConta.valorPago.isNotNull());
                    break;
                case 1:
                    builder.and(qConta.vencimento.after(LocalDate.now())).and(qConta.dataPagamento.isNull());
                    break;
                case 0:
                    builder.and(qConta.vencimento.eq(LocalDate.now())).and(qConta.dataPagamento.isNull());
                    break;
                case -1:
                    builder.and(qConta.vencimento.before(LocalDate.now())).and(qConta.dataPagamento.isNull());
                    break;
            }
        return builder;
    }
}
