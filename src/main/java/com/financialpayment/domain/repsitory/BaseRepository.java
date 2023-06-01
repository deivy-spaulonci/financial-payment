package com.financialpayment.domain.repsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID>, QuerydslPredicateExecutor<T> {
    Optional<T> findById(ID id);

    <S extends T> S save(S entity);

//    List<T> searchByFilter(T t, Sort sort);
//
//    Page<T> getPageByFilter(T t, Pageable pageable);

}
